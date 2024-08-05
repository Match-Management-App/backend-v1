package com.match_management.demo.chemicalPartner.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChemicalResponse {
    private String first;
    private String second;
    private String third;

    @Builder
    public ChemicalResponse(final String first, final String second, final String third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    @Override
    public String toString() {
        return "BestChemicalUserResponse{" +
                "first='" + first + '\'' +
                ", second='" + second + '\'' +
                ", third='" + third + '\'' +
                '}';
    }
}
