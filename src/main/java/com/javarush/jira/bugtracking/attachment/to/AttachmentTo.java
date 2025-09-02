package com.javarush.jira.bugtracking.attachment.to;

import com.javarush.jira.common.to.NamedTo;
import lombok.Getter;

@Getter
public class AttachmentTo extends NamedTo {
    public AttachmentTo(Long id, String name) {
        super(id, name);
    }
}

/* AttachmentTo — это простой DTO (Data Transfer Object), который наследует
от NamedTo. Он используется для передачи данных о вложении (файле) между
слоями приложения, typically из серверной части в клиентскую через REST API.

@Getter Обеспечивает доступ к данным объекта (в данном случае, унаследованным полям
id и name) для сериализации в JSON или использования в коде. Сеттеры не генерируются,
что может указывать на иммутабельность (неизменяемость) объекта после создания.

Зачем нужно: Для создания экземпляра этого DTO необходимо явно передать все данные.
Это подтверждает идею о том, что объект является иммутабельным
( его состояние нельзя изменить после создания).
Конструктор просто передает параметры в конструктор
родительского класса (super(id, name)).

 */