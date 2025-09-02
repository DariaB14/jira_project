package com.javarush.jira.common.util.validation;

import jakarta.validation.groups.Default;

public class View {
    public interface OnCreate extends Default {
    }

    public interface OnUpdate extends Default {
    }
}

/*
Это класс View с интерфейсами для Jackson JSON Views.
JSON Views - это механизм в Jackson для контроля того, какие поля включать
в JSON ответ при разных сценариях.
 */