## [REST API](http://localhost:8080/doc)

Веб-приложение  «Система управления задачами»  по аналогии с Jira / Trello. Предназначен для отслеживания активности: от ведения проектов до личных списков задач.

## Технологический стек
* Бэкенд: Spring Boot, Spring MVC, Spring Security, Spring Data JPA, Spring Test
* База данных: PostgreSQL, H2 (для тестов), Liquibase
* Безопасность & Инфраструктура: Caffeine, Docker, Nginx
* Утилиты: Lombok, MapStruct, Swagger 
* Тестирование: JUnit
* Фронтенд: Thymeleaf, jQuery

## Архитектура

Модульный монолит на основе Spring Modulith. Каждая доменная область оформлена как отдельный модуль с внутренними пакетами internal, что упрощает переход к микросервисной архитектуре в будущем.

## Выполненные задачи 

* Удалена интеграция с VK и Yandex ->	Упрощена кодовая база, убраны лишние зависимости
* Вынесены секреты в application-secrets.properties и переменные окружения ->	Повышена безопасность и соответствие 12-Factor App
* Настроена тестовая БД H2 и профили prod/test -> Удобное тестирование без поднятия PostgreSQL
* Реализованы тесты для ProfileRestController	-> Полное покрытие публичных методов, проверка success/unsuccess сценариев
* Рефакторинг FileUtil	-> Использование современного API NIO
* Добавлен функционал тегов для задач (REST API)	-> Возможность CRUD-операций с тегами
* Реализован подсчёт времени жизненного цикла задач	-> Методы getTimeInProgress и getTimeInTesting для аналитики
* Создан Dockerfile	-> Сборка и запуск приложения в контейнере
* Создан docker-compose.yml	-> Оркестрация приложения, PostgreSQL и Nginx

## Запуск

mvn clean package
docker-compose up --build

## Локальный запуск 
 docker run -p 5432:5432 --name postgres-db -e POSTGRES_USER=jira -e POSTGRES_PASSWORD=JiraRush -e POSTGRES_DB=jirarush -d postgres:15
 
mvn clean install

SPRING_PROFILES_ACTIVE=prod java -jar target/jirarush.jar  

Для заполнения БД тестовыми данными используйте скрипт resources/data4dev/data.sql

