package com.javarush.jira.login.internal.passwordreset;

import com.javarush.jira.common.AppEvent;
import com.javarush.jira.login.User;

public record PasswordResetEvent(User user, String token) implements AppEvent {
}

/*
Что содержит:
User user - пользователь, который запросил сброс пароля
String token - уникальный токен для подтверждения сброса
Зачем нужно: Это событие публикуется, когда пользователь запрашивает
сброс пароля. Слушатели событий могут обработать его (например,
отправить email с ссылкой для сброса).
 */