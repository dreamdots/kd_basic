package com.dots.kd_basic.transformer;

import com.dots.kd_basic.utils.DateUtils;
import com.dots.kd_model.model.IEntity;

import java.time.format.DateTimeFormatter;

public abstract class Transformer<T extends IEntity<?>, S> {
    protected final DateTimeFormatter formatter() {
        return DateUtils.formatter();
    }
}
