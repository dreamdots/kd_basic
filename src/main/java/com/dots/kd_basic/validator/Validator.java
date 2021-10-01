package com.dots.kd_basic.validator;

public abstract class Validator<X> {
    public abstract void validate(X obj) throws ValidatorFailedCheckException;
}
