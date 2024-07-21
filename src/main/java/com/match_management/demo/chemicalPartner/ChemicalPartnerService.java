package com.match_management.demo.chemicalPartner;

import com.match_management.demo.user.User;
import com.match_management.demo.user.UserRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChemicalPartnerService {
    private final ChemicalPartnerRepository chemicalPartnerRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long create(final Long goalMemberId, final Long assistMemberId,
                       final int stat, final LocalDate matchDate) {
        final ChemicalPartner chemicalPartner = chemicalPartnerRepository
                .findByGoalMemberIdAndAssistMemberId(goalMemberId, assistMemberId)
                        .orElse(new ChemicalPartner(goalMemberId, assistMemberId, 0L, matchDate));

        chemicalPartner.addStat(stat);
        chemicalPartnerRepository.save(chemicalPartner);

        return chemicalPartner.getId();
    }

    public List<String> getAssistPartner(final Long userId) {
        List<ChemicalPartner> chemicalPartners = chemicalPartnerRepository
                .findByGoalMemberIdAndOrderByStat(userId, PageRequest.of(0, 3));

        return chemicalPartners
                .stream()
                .map(c -> {
                    User user = userRepository
                            .findById(c.getAssistMemberId())
                            .orElseThrow(RuntimeException::new);
                    return user.getName();
                })
                .toList();
    }

    public List<String> getGoalPartner(final Long userId) {
        List<ChemicalPartner> chemicalPartners = chemicalPartnerRepository
                .findByAssistMemberIdAndOrderByStat(userId, PageRequest.of(0, 3));

        return chemicalPartners
                .stream()
                .map(c -> {
                    User user = userRepository
                            .findById(c.getGoalMemberId())
                            .orElseThrow(RuntimeException::new);
                    return user.getName();
                })
                .toList();
    }
}
