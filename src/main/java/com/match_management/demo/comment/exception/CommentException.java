package com.match_management.demo.comment.exception;

import com.match_management.demo.exception.CustomException;

public class CommentException extends CustomException {
    public CommentException(final CommentErrorCode commentErrorCode) {
        super(commentErrorCode);
    }

    public static class NoCommentException extends CommentException{
        public NoCommentException() {
            super(CommentErrorCode.NO_COMMENT);
        }
    }
}
