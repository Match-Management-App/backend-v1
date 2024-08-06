package com.match_management.demo.board.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AmendMatchDateRequest {
    private Long boardId;
    private LocalDateTime matchDate;
}
