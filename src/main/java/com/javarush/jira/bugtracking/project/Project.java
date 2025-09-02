package com.javarush.jira.bugtracking.project;

import com.javarush.jira.common.HasCode;
import com.javarush.jira.common.model.TitleEntity;
import com.javarush.jira.common.util.validation.Code;
import com.javarush.jira.common.util.validation.Description;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "project")
@Getter
@Setter
@NoArgsConstructor
public class Project extends TitleEntity implements HasCode {

    @Code
    // пользовательская аннотация
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "description", nullable = false)
    @Description
    // пользовательская аннотация
    private String description;

    // link to Reference.code with RefType.PROJECT
    /*Поясняет, что поле typeCode не является самостоятельным перечислением (enum)
    в коде Java. Вместо этого оно ссылается на запись в другой таблице (скорее всего,
    reference), где code является значением, а RefType.PROJECT — это тип справочника.
    Это распространенный паттерн для создания гибких справочников, управляемых
    через БД, а не через код.*/
    @Code
    @Column(name = "type_code", nullable = false)
    private String typeCode;

    @Nullable
    @ManyToOne(fetch = FetchType.LAZY)
    /*Указывает, что много Project могут относиться к одному родительскому Project.
    fetch = FetchType.LAZY — это стратегия загрузки. Она означает, что связанная
    сущность parent не будет загружена из БД сразу при загрузке текущего проекта.
    Данные о родителе будут подгружены только при первом обращении к этому полю
    (например, при вызове getParent()). Это важная оптимизация для производительности. */
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    /*поле (parent) нельзя использовать для вставки или обновления значения в колонке
    parent_id. Эта колонка управляется другим полем (см. ниже parentId). Данное поле
    parent предназначено только для чтения и навигации*/
    @OnDelete(action = OnDeleteAction.CASCADE)
    /*если родительский проект будет удален из БД, то все его дочерние проекты будут
    удалены автоматически на уровне базы данных.*/
    private Project parent;

    @Column(name = "parent_id")
    private Long parentId;

    public Project(Long id, String code, String title, String typeCode, String description, Long parentId) {
        super(id, title);
        this.code = code;
        this.parentId = parentId;
        this.description = description;
        this.typeCode = typeCode;
    }
}
