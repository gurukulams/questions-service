package com.gurukulams.questionbank.validation;

import com.gurukulams.questionbank.model.QuestionChoice;
import com.gurukulams.questionbank.payload.Question;
import com.gurukulams.questionbank.payload.QuestionType;
import com.gurukulams.questionbank.service.QuestionService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

/**
 * Type specific validation rules for a Question.
 */
public class QuestionValidator
        implements ConstraintValidator<ValidQuestion, Question> {

    /**
     * Validates question.
     * @param question
     * @param context
     * @return isValid
     */
    @Override
    public boolean isValid(final Question question,
                           final ConstraintValidatorContext context) {
        if (question == null || question.getType() == null) {
            return true;
        }

        context.disableDefaultConstraintViolation();

        final QuestionType type = question.getType();

        if (type.equals(QuestionType.MATCH_THE_FOLLOWING)) {
            return validateMatches(question, context);
        } else if (type.equals(QuestionType.MULTI_CHOICE)
                || type.equals(QuestionType.CHOOSE_THE_BEST)) {
            return validateChoices(question, context);
        }
        return validateAnswer(question, context);
    }

    private boolean validateMatches(final Question question,
                                    final ConstraintValidatorContext context) {
        final List<QuestionChoice> choices = question.getChoices();
        final List<QuestionChoice> matches = question.getMatches();
        boolean valid = true;

        if (choices == null || choices.isEmpty()) {
            valid = reject(context, "choices", "No choices are provided");
        }
        if (matches == null || matches.isEmpty()) {
            valid = reject(context, "matches", "No matches are provided");
        }
        if (valid && choices.size() > matches.size()) {
            valid = reject(context, "matches", "Not Enough Matches");
        }
        return valid;
    }

    private boolean validateChoices(final Question question,
                                    final ConstraintValidatorContext context) {
        final List<QuestionChoice> choices = question.getChoices();

        if (choices == null
                || choices.size() < QuestionService.MIN_CHOICES
                || choices.size() > QuestionService.MAX_CHOICES) {
            return reject(context, "choices",
                    "Minimum 2 or Maximum 5 choices");
        }
        if (choices.stream()
                .noneMatch(choice -> choice.answer() != null
                        && choice.answer())) {
            return reject(context, "choices",
                    "At-least One Answer should be available");
        }
        return true;
    }

    private boolean validateAnswer(final Question question,
                                   final ConstraintValidatorContext context) {
        if (question.getAnswer() == null) {
            return reject(context, "answer", "Answer should not be empty");
        }
        return true;
    }

    /**
     * Registers a violation on the given property.
     * @param context
     * @param message
     * @param property
     * @return always false, so callers can assign it to a valid flag
     */
    private boolean reject(final ConstraintValidatorContext context,
                           final String property,
                           final String message) {
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(property)
                .addConstraintViolation();
        return false;
    }
}
