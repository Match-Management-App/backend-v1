package com.match_management.demo.chemicalPartner;

import com.match_management.demo.chemicalPartner.dto.ChemicalResponse;
import java.time.LocalDate;
import java.util.List;

import com.match_management.demo.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChemicalPartnerService {
    private final ChemicalPartnerRepository chemicalPartnerRepository;
    private final UserService userService;

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

    public ChemicalResponse getBestAssistPartner(final Long userId) {
        final List<ChemicalPartner> chemicalPartners = chemicalPartnerRepository
                .findByGoalMemberIdAndOrderByStat(userId, PageRequest.of(0, 3));

        return ChemicalResponse
                .builder()
                .first(userService.getUserNameByAssistId(chemicalPartners.get(0).getAssistMemberId()))
                .second(userService.getUserNameByAssistId(chemicalPartners.get(1).getAssistMemberId()))
                .third(userService.getUserNameByAssistId(chemicalPartners.get(2).getAssistMemberId()))
                .build();
    }

    public ChemicalResponse getBestGoalPartner(final Long userId) {
        final List<ChemicalPartner> chemicalPartners = chemicalPartnerRepository
                .findByAssistMemberIdAndOrderByStat(userId, PageRequest.of(0, 3));

        return ChemicalResponse
                .builder()
                .first(userService.getUserNameByGoalId(chemicalPartners.get(0).getGoalMemberId()))
                .second(userService.getUserNameByGoalId(chemicalPartners.get(1).getGoalMemberId()))
                .third(userService.getUserNameByGoalId(chemicalPartners.get(2).getGoalMemberId()))
                .build();
    }

    public ChemicalResponse getWorstAssistPartner(final Long userId) {
        final List<ChemicalPartner> chemicalPartners = chemicalPartnerRepository
                .findByWorstAssistMemberIdAndOrderByStat(userId, PageRequest.of(0, 3));

        return ChemicalResponse
                .builder()
                .first(userService.getUserNameByAssistId(chemicalPartners.get(0).getAssistMemberId()))
                .second(userService.getUserNameByAssistId(chemicalPartners.get(1).getAssistMemberId()))
                .third(userService.getUserNameByAssistId(chemicalPartners.get(2).getAssistMemberId()))
                .build();
    }

    public ChemicalResponse getWorstGoalPartner(final Long userId) {
        final List<ChemicalPartner> chemicalPartners = chemicalPartnerRepository
                .findByWorstGoalMemberIdAndOrderByStat(userId, PageRequest.of(0, 3));

        return ChemicalResponse
                .builder()
                .first(userService.getUserNameByGoalId(chemicalPartners.get(0).getGoalMemberId()))
                .second(userService.getUserNameByGoalId(chemicalPartners.get(1).getGoalMemberId()))
                .third(userService.getUserNameByGoalId(chemicalPartners.get(2).getGoalMemberId()))
                .build();
    }
}
