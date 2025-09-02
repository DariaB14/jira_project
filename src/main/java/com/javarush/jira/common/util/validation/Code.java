package com.javarush.jira.common.util.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
//Показывает что эта аннотация является частью контракта API
@Constraint(validatedBy = {})
//Помечает как аннотацию валидации Bean Validation
//validatedBy = {} - пустой массив, значит использует композицию аннотаций
@Target({METHOD, FIELD})
@Retention(RUNTIME)
@NotBlank
@NoHtml
//защита от XSS, запрещает HTML теги
@Size(min = 2, max = 32)
public @interface Code {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

/*
Это стандартные методы для аннотаций Bean Validation:

message() - сообщение об ошибке:
default "" - пустое, будет использоваться сообщение из композитных аннотаций

groups() - группы валидации:
Позволяет включать/выключать валидацию для разных сценариев

payload() - метаданные:
Можно передать дополнительную информацию для валидации
 */