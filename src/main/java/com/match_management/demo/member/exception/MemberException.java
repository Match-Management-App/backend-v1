package com.match_management.demo.member.exception;

import com.match_management.demo.exception.CustomException;

public class MemberException extends CustomException {
    public MemberException(final MemberErrorCode memberErrorCode) {
        super(memberErrorCode);
    }

    public static class UnMatchedCodeException extends MemberException {
        public UnMatchedCodeException() {
            super(MemberErrorCode.UNMATCHED_CODE);
        }
    }

    public static class NoMemberException extends MemberException {
        public NoMemberException() {
            super(MemberErrorCode.NO_USER);
        }
    }
}
