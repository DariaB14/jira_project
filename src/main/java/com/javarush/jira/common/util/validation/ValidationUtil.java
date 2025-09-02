package com.javarush.jira.common.util.validation;

import com.javarush.jira.common.HasId;
import com.javarush.jira.common.error.IllegalRequestDataException;
import lombok.experimental.UtilityClass;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.lang.NonNull;

@UtilityClass
/*
Lombok аннотация для создания утилитарного класса
Автоматически делает:
Конструктор private (нельзя создать экземпляр)
Все методы static
Класс final (нельзя наследовать)
 */
public class ValidationUtil {

    //проверяет что объект новый
    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must be new (id=null)");
        }
    }

    //  Conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)

    //проверка согласованности id
    public static void assureIdConsistent(HasId bean, long id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must has id=" + id);
        }
    }

    /* Применяет assureIdConsistent к каждому элементу коллекции
    Полезно для bulk операций */
    public static void assureIdConsistent(Iterable<? extends HasId> beans, long id) {
        beans.forEach(b -> assureIdConsistent(b, id));
    }

    //  https://stackoverflow.com/a/65442410/548473
    @NonNull
    //поиск корневой причины исключения
    public static Throwable getRootCause(@NonNull Throwable t) {
        Throwable rootCause = NestedExceptionUtils.getRootCause(t);
        return rootCause != null ? rootCause : t;
    }
}