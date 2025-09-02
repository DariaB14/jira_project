package com.javarush.jira.login.internal.passwordreset;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

@Getter
@ToString
public class ResetData implements Serializable {
    private final String token;
    private final String email;

    public ResetData(@NotNull String email) {
        this.token = UUID.randomUUID().toString();
        this.email = email;
    }
}

/*
Что содержит:
String token - уникальный токен (генерируется автоматически при создании)
String email - email пользователя
implements Serializable - может быть сериализован (например, для хранения в сессии
или кэше)
Зачем нужно: Хранит данные, необходимые для процесса сброса пароля.
likely используется для временного хранения данных между запросами.
 */