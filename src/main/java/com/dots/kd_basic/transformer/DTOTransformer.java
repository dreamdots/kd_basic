package com.dots.kd_basic.transformer;

import com.dots.kd_model.model.IEntity;

public abstract class DTOTransformer<T extends IEntity<?>, S> extends Transformer<T, S> {
    public abstract S toDTO(T entity);
}
