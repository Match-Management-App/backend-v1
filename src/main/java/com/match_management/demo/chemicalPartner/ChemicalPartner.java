package com.match_management.demo.chemicalPartner;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChemicalPartner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long goalMemberId;
    private Long assistMemberId;
    private Long stat;
    private LocalDate matchDate;

    public ChemicalPartner(final Long goalMemberId, final Long assistMemberId,
                           final Long stat, final LocalDate matchDate) {
        this.goalMemberId = goalMemberId;
        this.assistMemberId = assistMemberId;
        this.stat = stat;
        this.matchDate = matchDate;
    }

    public void addStat(final int stat) {
        this.stat += stat;
    }
}
