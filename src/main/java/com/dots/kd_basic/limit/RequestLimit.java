package com.dots.kd_basic.limit;

import java.util.concurrent.TimeUnit;

public @interface RequestLimit {
    int operationPerTime() default 1;
    TimeUnit timeType() default TimeUnit.SECONDS;
}
