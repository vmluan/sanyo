/**
 * Copyright (c) 2015  osgicse group.
 * Filename   : EntityHelper.java
 * Description: 
 * @author    : Luan Vo
 * Created    : Sep 3, 2013
 */


package com.sanyo.quote.helper;

public interface EntityHelper<T> {

    T copyFrom(final T entity);

    T copyWithoutPkFrom(final T entity);

    T updateFrom(final T fromentity, T toEntity);

    T createEntityInstance();

    T createRandomEntity();
}
