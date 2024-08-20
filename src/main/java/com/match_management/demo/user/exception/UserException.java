package com.match_management.demo.user.exception;

import com.match_management.demo.exception.CustomException;

public class UserException extends CustomException {
    public UserException(final UserErrorCode userErrorCode) {
        super(userErrorCode);
    }

    public static class UnMatchedCodeException extends UserException {
        public UnMatchedCodeException() {
            super(UserErrorCode.UNMATCHED_CODE);
        }
    }

    public static class NoUserException extends UserException {
        public NoUserException() {
            super(UserErrorCode.NO_USER);
        }
    }
}
