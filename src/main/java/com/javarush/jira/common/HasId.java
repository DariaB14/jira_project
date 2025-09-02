package com.javarush.jira.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.Assert;

public interface HasId {
    Long getId();

    void setId(Long id);

    @JsonIgnore
    //не сериализуется в JSON.
    default boolean isNew() {
        return getId() == null;
    }
//    Возвращает true, если объект еще не сохранен в БД (его id == null).
//    Для определения, нужно ли выполнять INSERT или UPDATE при сохранении.

    // doesn't work for hibernate lazy proxy
    default long id() {
        Assert.notNull(getId(), "Entity must has id");
        return getId();
    }
}
