package com.match_management.demo.chemicalPartner;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChemicalPartnerRepository extends JpaRepository<ChemicalPartner, Long> {
    @Query("select c from ChemicalPartner c "
            + "where c.goalMemberId = :goalMemberId and c.assistMemberId = :assistMemberId")
    Optional<ChemicalPartner> findByGoalMemberIdAndAssistMemberId
            (@Param("goalMemberId") final Long goalMemberId,
             @Param("assistMemberId") final Long assistMemberId
            );

    @Query("select c from ChemicalPartner c "
            + "where c.goalMemberId = :userId ORDER BY c.stat DESC")
    List<ChemicalPartner> findByGoalMemberIdAndOrderByStat(
            @Param("userId") final Long userId,
            final Pageable pageable
    );
}
