package com.match_management.demo.comment.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentsResponse {
    private Long id;
    private String userName;
    private String text;
    private LocalDateTime date;

    @Builder
    public CommentsResponse(final Long id, final String userName, final String text, final LocalDateTime date) {
        this.userName = userName;
        this.text = text;
        this.date = date;
    }
}
