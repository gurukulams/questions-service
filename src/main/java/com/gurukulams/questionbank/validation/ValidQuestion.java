package com.gurukulams.questionbank.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validates the type specific rules of a Question
 * (choices, matches and answer).
 *
 * Use with the {@link QuestionRules} group:
 * {@code @ValidQuestion(groups = QuestionRules.class)}
 */
@Documented
@Constraint(validatedBy = QuestionValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidQuestion {

    /**
     * Default message.
     *
     * @return message
     */
    String message() default "Invalid question";

    /**
     * Validation groups.
     *
     * @return groups
     */
    Class<?>[] groups() default { };

    /**
     * Payload.
     *
     * @return payload
     */
    Class<? extends Payload>[] payload() default { };
}
