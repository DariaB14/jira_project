package com.javarush.jira.profile.internal.web;

import com.javarush.jira.profile.ContactTo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Data
public class ProfilePostRequest {
    private Set<@NotBlank String> mailNotifications;
    //Набор кодов уведомлений, которые пользователь хочет получать.

    private @Valid ContactTo[] contacts;
    //Массив контактов пользователя. Аннотация @Valid включает валидацию вложенных объектов.
}

/*
: DTO для входящего запроса на обновление профиля пользователя.
 */