package com.match_management.demo.auth.jwt;

import com.match_management.demo.auth.AuthUser;
import com.match_management.demo.auth.jwt.exception.JwtException;
import com.match_management.demo.user.User;
import com.match_management.demo.user.UserRepository;
import io.jsonwebtoken.Claims;
import com.match_management.demo.user.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
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
    private final UserRepository userRepository;

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

        final User user = userRepository.findByOauthId(oauthId).orElseThrow(RuntimeException::new);

        return new UsernamePasswordAuthenticationToken(
                new AuthUser(user.getOauthId(), user.getName()),
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
}