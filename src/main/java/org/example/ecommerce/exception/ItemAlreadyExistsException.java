package org.example.ecommerce.exception;

import lombok.Getter;

@Getter
public class ItemAlreadyExistsException extends RuntimeException {
    private final String errMsgKey;
    private final String errMsgCode;

    public ItemAlreadyExistsException(String message) {
        super(message);
        this.errMsgKey = ErrorCode.GENERIC_ALREADY_EXISTS.getErrMsgKey();
        this.errMsgCode = ErrorCode.GENERIC_ALREADY_EXISTS.getErrCode();
    }
}
