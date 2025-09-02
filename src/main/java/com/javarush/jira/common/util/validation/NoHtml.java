package com.javarush.jira.common.util.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = NoHtmlValidator.class)
//NoHtmlValidator.class - собственная логика валидации
@Target({METHOD, FIELD, ANNOTATION_TYPE})
//ANNOTATION_TYPE - можно использовать в других аннотациях (как в @Code)
@Retention(RUNTIME)
public @interface NoHtml {
    String message() default "{error.noHtml}";
    //Сообщение из properties - {error.noHtml}

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
