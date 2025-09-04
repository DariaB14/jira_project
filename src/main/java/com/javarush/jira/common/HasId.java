package com.javarush.jira.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.Assert;

public interface HasId {
    Long getId();

    void setId(Long id);

    @JsonIgnore
    default boolean isNew() {
        return getId() == null;
    }

    default long id() {
        Assert.notNull(getId(), "Entity must has id");
        return getId();
    }
}
