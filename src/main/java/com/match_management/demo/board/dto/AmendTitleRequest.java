package com.match_management.demo.board.dto;

import lombok.Getter;

@Getter
public class AmendTitleRequest {
    private Long boardId;
    private String title;
}
