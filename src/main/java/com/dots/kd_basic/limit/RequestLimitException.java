package com.dots.kd_basic.limit;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class RequestLimitException extends RuntimeException {
    private final Method target;
    private final long operationPer;
    private final TimeUnit timeType;

    public RequestLimitException(Method target, long operationPer, TimeUnit timeType) {
        super("Method: '" + target.getName() + "' has a limit of " + operationPer + " requests per " + timeType.toString().toLowerCase());
        this.target = target;
        this.operationPer = operationPer;
        this.timeType = timeType;
    }
}
