package com.match_management.demo.record.exception;

import com.match_management.demo.exception.CustomException;

public class RecordException extends CustomException {
    public RecordException(final RecordErrorCode recordErrorCode) {
        super(recordErrorCode);
    }

    public static class NoRecordException extends RecordException{
        public NoRecordException() {
            super(RecordErrorCode.NO_RECORD);
        }
    }
}
