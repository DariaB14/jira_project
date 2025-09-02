package com.javarush.jira.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@UtilityClass
/*
JsonUtil - это обёртка над Jackson ObjectMapper которая:
Упрощает работу с JSON
Обрабатывает исключения и предоставляет понятные ошибки
Добавляет полезные методы для частых операций
 */
public class JsonUtil {
    private static ObjectMapper mapper;

    public static void setMapper(ObjectMapper mapper) {
        JsonUtil.mapper = mapper;
    }
    //Позволяет использовать единый mapper во всём приложении

    public static <T> List<T> readValues(String json, Class<T> clazz) {
        //Читает JSON массив и преобразует в список объектов
        //Использует ObjectReader для эффективного чтения
        ObjectReader reader = mapper.readerFor(clazz);
        try {
            return reader.<T>readValues(json).readAll();
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid read array from JSON:\n'" + json + "'", e);
        }
    }

    public static <T> T readValue(String json, Class<T> clazz) {
       //Читает JSON объект и преобразует в Java объект
        //Базовый метод для десериализации
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid read from JSON:\n'" + json + "'", e);
        }
    }

    public static <T> String writeValue(T obj) {
        //Преобразует Java объект в JSON строку
        //Сериализация объекта
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Invalid write to JSON:\n'" + obj + "'", e);
        }
    }

    public static <T> String writeAdditionProps(T obj, String addName, Object addValue) {
        //Конвертирует объект в Map
        //Добавляет новое свойство в Map
        //Преобразует Map обратно в JSON
        Map<String, Object> map = mapper.convertValue(obj, new TypeReference<>() {
        });
        map.put(addName, addValue);
        return writeValue(map);
    }
}