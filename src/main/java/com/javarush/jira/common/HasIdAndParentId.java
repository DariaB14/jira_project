package com.javarush.jira.common;

public interface HasIdAndParentId extends HasId {
    Long getParentId();
}

//Для сущностей, которые участвуют в иерархических отношениях (задачи, проекты).
//Позволяет строить деревья и универсально обрабатывать иерархические данные.