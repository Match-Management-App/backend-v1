package com.match_management.demo.comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 200)
    private String text;
    private Long userId;
    private Long boardId;
    private LocalDateTime updatedAt;

    @Builder
    public Comment(final Long userId, final Long boardId, final String text) {
        this.userId = userId;
        this.boardId = boardId;
        this.text = text;
        this.updatedAt = LocalDateTime.now();
    }

    public void amend(final String text) {
        this.text = text;
    }
}
