package com.match_management.demo.chemicalPartner;

import com.match_management.demo.chemicalPartner.dto.ChemicalResponse;
import com.match_management.demo.member.Member;
import com.match_management.demo.record.RecordService;
import java.time.LocalDate;
import java.util.List;

import com.match_management.demo.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChemicalPartnerService {
    private final ChemicalPartnerRepository chemicalPartnerRepository;
    private final MemberService memberService;
    private final RecordService recordService;

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
                .first(memberService.getMemberNameByAssistId(chemicalPartners.get(0).getAssistMemberId()))
                .second(memberService.getMemberNameByAssistId(chemicalPartners.get(1).getAssistMemberId()))
                .third(memberService.getMemberNameByAssistId(chemicalPartners.get(2).getAssistMemberId()))
                .build();
    }

    public ChemicalResponse getBestGoalPartner(final Long userId) {
        final List<ChemicalPartner> chemicalPartners = chemicalPartnerRepository
                .findByAssistMemberIdAndOrderByStat(userId, PageRequest.of(0, 3));

        return ChemicalResponse
                .builder()
                .first(memberService.getMemberNameByGoalId(chemicalPartners.get(0).getGoalMemberId()))
                .second(memberService.getMemberNameByGoalId(chemicalPartners.get(1).getGoalMemberId()))
                .third(memberService.getMemberNameByGoalId(chemicalPartners.get(2).getGoalMemberId()))
                .build();
    }

    public ChemicalResponse getWorstAssistPartner(final Long userId) {
        final List<ChemicalPartner> chemicalPartners = chemicalPartnerRepository
                .findByWorstAssistMemberIdAndOrderByStat(userId, PageRequest.of(0, 3));

        return ChemicalResponse
                .builder()
                .first(memberService.getMemberNameByAssistId(chemicalPartners.get(0).getAssistMemberId()))
                .second(memberService.getMemberNameByAssistId(chemicalPartners.get(1).getAssistMemberId()))
                .third(memberService.getMemberNameByAssistId(chemicalPartners.get(2).getAssistMemberId()))
                .build();
    }

    public ChemicalResponse getWorstGoalPartner(final Long userId) {
        final List<ChemicalPartner> chemicalPartners = chemicalPartnerRepository
                .findByWorstGoalMemberIdAndOrderByStat(userId, PageRequest.of(0, 3));

        return ChemicalResponse
                .builder()
                .first(memberService.getMemberNameByGoalId(chemicalPartners.get(0).getGoalMemberId()))
                .second(memberService.getMemberNameByGoalId(chemicalPartners.get(1).getGoalMemberId()))
                .third(memberService.getMemberNameByGoalId(chemicalPartners.get(2).getGoalMemberId()))
                .build();
    }

    @Transactional
    public void init() {
        // 전체 골 순위
        // 민진 -> 수환 -> 용민 순

        // 전체 어시스트 순위
        // 수환 -> 민진 -> 리나 순

        // 수비 순위
        // 용민 -> 소현 -> 민진 순

        //민진 입장
        // 골 케미
        // 1등 민진(어시) - 수환
        // 2등 민진 - 리나
        // 3등 민진 - 용민

        //어시 케미
        // 1등 민진(골) - 용민
        // 2등 민진 - 수환
        // 3등 민진 - 소현

        //수환 입장
        //골 케미 (내가 어시를 해준거)
        // 1등 민진
        // 2등 소현
        // 3등 리나

        //어시 케미 (내가 어시를 받은거)
        // 1등 민진
        // 2등 리나
        // 3등 용민
        memberService.create(123L, "박수환", "midfielder");
        memberService.create(124L, "박용민", "Striker");
        memberService.create(125L, "박소현", "Striker");
        memberService.create(126L, "박리나", "defender");

        recordService.accumulateGoalsStat(3657521361L, 6);
        recordService.accumulateGoalsStat(123L, 5);
        recordService.accumulateGoalsStat(124L, 2);

        recordService.accumulateAssistsStat(123L, 6);
        recordService.accumulateAssistsStat(3657521361L, 4);
        recordService.accumulateAssistsStat(126L, 2);

        recordService.accumulateDefencesStat(124L, 6);
        recordService.accumulateDefencesStat(125L, 4);
        recordService.accumulateDefencesStat(3657521361L, 1);

        //참석 순위 민진 수환 용민
        recordService.accumulateAttendanceStat(3657521361L, 10);
        recordService.accumulateAttendanceStat(123L, 9);
        recordService.accumulateAttendanceStat(124L, 5);

        //민진 입장
        // 골 케미
        // 1등 민진(어시) - 수환
        // 2등 민진 - 리나
        // 3등 민진 - 용민

        create(123L, 3657521361L, 5, LocalDate.now());
        create(126L, 3657521361L, 3, LocalDate.now());
        create(124L, 3657521361L, 2, LocalDate.now());
        //어시 케미
        // 1등 민진(골) - 용민
        // 2등 민진 - 수환
        // 3등 민진 - 소현
        create(3657521361L, 124L, 4, LocalDate.now());
        create(3657521361L, 123L, 3, LocalDate.now());
        create(3657521361L, 125L, 2, LocalDate.now());

        //수환 입장
        //골 케미 (내가 어시를 해준거)
        // 1등 민진
        // 2등 소현
        // 3등 리나
        create(125L, 123L, 3, LocalDate.now());
        create(126L, 123L, 2, LocalDate.now());

        //어시 케미 (내가 어시를 받은거)
        // 1등 민진
        // 2등 리나
        // 3등 용민
        create(123L, 124L, 1, LocalDate.now());
    }
}
