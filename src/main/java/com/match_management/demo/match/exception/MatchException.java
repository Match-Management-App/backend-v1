package com.match_management.demo.match.exception;

import com.match_management.demo.exception.CustomException;
import com.match_management.demo.match.Match;

public class MatchException extends CustomException {
    public MatchException(final MatchErrorCode matchErrorCode) {
        super(matchErrorCode);
    }

    public static class NoMatchException extends MatchException{
        public NoMatchException() {
            super(MatchErrorCode.NO_MATCH_DATE);
        }
    }
}
