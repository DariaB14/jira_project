package com.javarush.jira.login.internal.verification;

import com.javarush.jira.common.AppEvent;
import com.javarush.jira.login.UserTo;

public record RegistrationConfirmEvent(UserTo userto, String token)
                    implements AppEvent {
}

//Это событие публикуется, когда пользователь завершает регистрацию и нужно
// подтвердить его email. Слушатели событий могут обработать его (например, отправить
// приветственное письмо).