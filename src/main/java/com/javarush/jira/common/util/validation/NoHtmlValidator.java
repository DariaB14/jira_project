package com.javarush.jira.common.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

public class NoHtmlValidator implements ConstraintValidator<NoHtml, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext ctx) {
        return value == null || Jsoup.isValid(value, Safelist.none());
        /*
        Jsoup.isValid(value, Safelist.none()) - проверяет что строка не содержит HTML
        Safelist.none() - запрещает ВСЕ HTML теги
        Возвращает true если: значение null или не содержит HTML
         */
    }
}
