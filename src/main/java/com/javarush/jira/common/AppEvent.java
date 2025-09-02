package com.javarush.jira.common;

// https://spring.io/blog/2015/02/11/better-application-events-in-spring-framework-4-2
public interface AppEvent {
}

/*
Пустой интерфейс-маркер.
Зачем нужно: Помечает классы как события приложения. Используется в системе событий
(event publishing/listening) Spring Framework. Все record'ы и классы ниже реализуют
этот интерфейс, что означает, что они могут быть опубликованы как события.
 */