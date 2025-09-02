package com.javarush.jira.bugtracking.project.to;

import com.javarush.jira.common.to.CodeTo;
import lombok.Value;

@Value
//создает иммутабельный (неизменяемый) класс.
//Все поля становятся private и final.
//Не генерируются сеттеры.
//Генерируются equals(), hashCode(), toString().
//Генерируется конструктор со всеми аргументами.
public class ProjectToFull extends ProjectTo {
    CodeTo parent;
    /*
    Позволяет клиенту получить сразу основные данные о родительском проекте
    (как минимум, его id, code и enabled) без необходимости делать дополнительный
    запрос к API. Это улучшает эффективность и удобство.
     */

    public ProjectToFull(Long id, String code, String title, String description, String typeCode, CodeTo parent) {
        super(id, code, title, description, typeCode, parent == null ? null : parent.getId());
        this.parent = parent;
    }
}