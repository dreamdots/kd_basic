package com.dots.kd_basic.validator;

public class ValidatorFailedCheckException extends RuntimeException {
    public ValidatorFailedCheckException(String message) {
        super(message);
    }
}
