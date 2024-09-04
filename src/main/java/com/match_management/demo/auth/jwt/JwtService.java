package com.match_management.demo.auth.jwt;

import com.match_management.demo.auth.AuthUser;
import com.match_management.demo.auth.jwt.dto.ReissueRequest;
import com.match_management.demo.auth.jwt.dto.ReissueResponse;
import com.match_management.demo.auth.jwt.exception.JwtException;
import com.match_management.demo.cookie.CookieShop;
import com.match_management.demo.user.Member;
import com.match_management.demo.user.MemberRepository;
import com.match_management.demo.user.exception.MemberException.NoMemberException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${accesstoken.expire.time}")
    private long accessTokenExpireTime;
    @Value("${refreshtoken.expire.time}")
    private long refreshTokenExpireTime;
    @Value("${jwt.token.secret}")
    private String code;
    private static final String ACCESS = "accessToken";
    private static final String REFRESH = "refreshToken";
    private final MemberRepository memberRepository;

    public String createAccessToken(final Long oauthId, final String name) {
        return createToken(oauthId, name, accessTokenExpireTime, ACCESS);
    }

    public String createRefreshToken(final Long oauthId, final String name) {
        return createToken(oauthId, name, refreshTokenExpireTime, REFRESH);
    }

    private String createToken(final Long oauthId, final String name,
                               final long time, final String type)
    {
        final Claims claims = Jwts.claims().setSubject("USER");

        claims.put("oauthId", oauthId);
        claims.put("name", name);
        claims.put("type", type);
        claims.put("roles", "USER");

        final Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setIssuer("Match-management")
                .setExpiration(new Date(now.getTime() + time))
                .signWith(generateSecretKey(code), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key generateSecretKey(final String secretCode) {
        final String encodedSecretCode = Base64.getEncoder().encodeToString(secretCode.getBytes());
        return Keys.hmacShaKeyFor(encodedSecretCode.getBytes());
    }

    public Authentication getAuthentication(final HttpServletRequest request, final String token) {
        final Claims claims = parseClaim(token);

        final Collection<? extends GrantedAuthority> authorities = Stream.of(
                        claims.get("roles").toString())
                .map(SimpleGrantedAuthority::new)
                .toList();

        final Long oauthId = claims.get("oauthId", Long.class);

        final Member member = memberRepository.findByOauthId(oauthId).orElseThrow(NoMemberException::new);

        return new UsernamePasswordAuthenticationToken(
                new AuthUser(member.getOauthId(), member.getName()),
                "",
                authorities
        );
    }

    private Claims parseClaim(final String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(generateSecretKey(code))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (MalformedJwtException e) {
            throw new JwtException.InvalidedToken();
        } catch (ExpiredJwtException e) {
            throw new JwtException.ExpiredToken();
        } catch (UnsupportedJwtException e) {
            throw new JwtException.UnSupportedToken();
        } catch (IllegalArgumentException e) {
            throw new JwtException.IllegalToken();
        } catch (SignatureException e) {
            throw new JwtException.WrongSignedToken();
        }
    }

    public Optional<String> extractToken(final HttpServletRequest request) {
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Strings.isEmpty(authorization)) {
            return Optional.empty();
        }
        return getToken(authorization.split(" "));
    }

    private Optional<String> getToken(final String[] token) {
        if (token.length != 2 || !Objects.equals(token[0], "Bearer")) {
            return Optional.empty();
        }
        return Optional.ofNullable(token[1]);
    }

    public ReissueResponse reissue(
            final HttpServletResponse response,
            final ReissueRequest reissueRequest,
            final String accessToken,
            final String refreshToken
    )
    {
        if (!Objects.equals(reissueRequest.getExpiredToken(), accessToken)) {
            throw new JwtException.UnMatchedTokenException();
        }

        //refreshToken의 oauthId와 authentication의 oauthId가 일치하는지 확인
        final Claims claims = parseClaim(refreshToken);
        final Member member = memberRepository.findByOauthId((Long) claims.get("oauthId"))
                .orElseThrow(JwtException.InValidUser::new);

        // 토큰 발행
        final String newAccessToken = createAccessToken(member.getOauthId(), member.getName());

        //다시 새로 쿠키에 추가
        CookieShop.bake(response, 30 * 60, "accessToken", newAccessToken);

        return ReissueResponse
                .builder()
                .accessToken(newAccessToken)
                .build();
    }
}
