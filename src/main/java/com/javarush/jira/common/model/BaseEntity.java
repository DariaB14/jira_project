package com.javarush.jira.common.model;

import com.javarush.jira.common.HasId;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;
import org.springframework.data.util.ProxyUtils;
import org.springframework.util.Assert;

@MappedSuperclass
/*Помечает класс как суперкласс, поля и аннотации которого должны быть
унаследованы классами-сущностями. */
//  https://stackoverflow.com/a/6084701/548473
@Access(AccessType.FIELD)
/*JPA должен получать доступ к полям сущности напрямую (через reflection),
а не через геттеры/сеттеры.*/
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseEntity implements Persistable<Long>, HasId {
    /* Persistable<Long>: Интерфейс Spring Data. Позволяет фреймворку понять,
    является ли сущность новой (ещё не сохраненной в БД) через метод isNew().
    Это важно для корректной работы метода save().
    HasId: Скорее всего, пользовательский интерфейс, гарантирующий наличие метода
    getId(). */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true)
    /* Указывает, что это поле не нужно показывать в документации API.
    ID обычно генерируется на стороне сервера, и его не следует передавать
    при создании нового объекта (в POST-запросе). */
    protected Long id;

    // doesn't work for hibernate lazy proxy
    public long id() {
        Assert.notNull(id, "Entity must have id");
        return id;
    }

    @Override
    public boolean isNew() {
        /* Spring Data использует этот метод, чтобы определить, нужно ли при вызове
        save() делать операцию INSERT (если isNew() возвращает true) или
        UPDATE (если false). */
        return id == null;
    }

    //    https://stackoverflow.com/questions/1638723
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(ProxyUtils.getUserClass(o))) {
            return false;
        }
        BaseEntity that = (BaseEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + id;
    }
}