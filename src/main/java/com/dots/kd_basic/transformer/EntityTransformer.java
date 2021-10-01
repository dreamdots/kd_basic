package com.dots.kd_basic.transformer;

import com.dots.kd_model.model.IEntity;

public abstract class EntityTransformer<T extends IEntity<?>, S> extends Transformer<T, S> {
    public abstract T toEntity(S entity);
}
