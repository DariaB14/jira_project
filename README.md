## [REST API](http://localhost:8080/doc)

## Концепция:

- Spring Modulith
    - [Spring Modulith: достигли ли мы зрелости модульности](https://habr.com/ru/post/701984/)
    - [Introducing Spring Modulith](https://spring.io/blog/2022/10/21/introducing-spring-modulith)
    - [Spring Modulith - Reference documentation](https://docs.spring.io/spring-modulith/docs/current-SNAPSHOT/reference/html/)

```
  url: jdbc:postgresql://localhost:5432/jira
  username: jira
  password: JiraRush
```

- Есть 2 общие таблицы, на которых не fk
    - _Reference_ - справочник. Связь делаем по _code_ (по id нельзя, тк id привязано к окружению-конкретной базе)
    - _UserBelong_ - привязка юзеров с типом (owner, lead, ...) к объекту (таска, проект, спринт, ...). FK вручную будем
      проверять

## Аналоги

- https://java-source.net/open-source/issue-trackers

## Тестирование

- https://habr.com/ru/articles/259055/


В проект подключен Swagger, который отвечает за документирование API. Для того чтоб посмотреть - при запущенном
приложении зайди на http://localhost:8080/swagger-ui/index.html


Список выполненных задач:
...

1. + 
2. +
3. +
4. +
5. +
6. +
7. +

8. Добавить подсчет времени сколько задача находилась в работе и тестировании. Написать 2 метода на уровне сервиса,
которые параметром принимают задачу и возвращают затраченное время:
      • Сколько задача находилась в работе (ready_for_review минус in_progress).
      • Сколько задача находилась на тестировании (done минус ready_for_review).
9. 
10. 
      Для написания этого задания, нужно добавить в конец скрипта инициализации базы данных test-changelog.sql 3 записи в
      таблицу ACTIVITY
      insert into ACTIVITY ( ID, AUTHOR_ID, TASK_ID, UPDATED, STATUS_CODE ) values .•.
      Со статусами:
      • время начала работы над задачей - in_progress
      • время окончания разработки - ready_for_review
      • время конца тестирования - done

9. Написать Dockerfile для основного сервера

10. Написать docker-compose файл для запуска контейнера сервера вместе с БД и nginx. Для ngin используй конфиг-файл
    config/nginx.conf . При необходимости файл конфига можно редактировать. Hard task

11. Добавить локализацию минимум на двух языках для шаблонов писем (mails) и стартовой страницы index.html.

12. Переделать механизм распознавания «свой-чужой» между фронтом и беком с JSESSIONID на JWT . Из сложностей - тебе
    придётся переделать отправку форм с фронта, чтоб добавлять хедер аутентификации.
    Extra-hard task