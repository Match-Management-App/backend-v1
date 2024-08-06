package com.match_management.demo.board.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardIdResponse {
    private Long boardId;

    @Builder
    public BoardIdResponse(final Long boardId) {
        this.boardId = boardId;
    }
}
