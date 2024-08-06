package com.match_management.demo.board.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardResponse {
    private Long boardId;
    private String title;
    private String writer;
    private LocalDateTime matchDate;
    private int participant;
    private int absentee;

    @Builder
    public BoardResponse(final Long boardId, final String title, final String writer, final LocalDateTime matchDate,
                         final int participant, final int absentee)
    {
        this.boardId = boardId;
        this.title = title;
        this.writer = writer;
        this.matchDate = matchDate;
        this.participant = participant;
        this.absentee = absentee;
    }
}
