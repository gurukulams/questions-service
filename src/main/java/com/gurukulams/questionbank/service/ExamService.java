package com.gurukulams.questionbank.service;

import com.gurukulams.questionbank.payload.Question;
import com.gurukulams.questionbank.model.Exam;

import java.util.UUID;

/**
 * Service For conducting and evaluating the exams.
 */
public class ExamService {
    /**
     * Store questions.
     */
    private final QuestionService questionService;

    /**
     * Validates answers.
     */
    private final AnswerService answerService;

    /**
     * Builds Exam Service.
     * @param theQuestionService
     * @param theAnswerService
     */
    public ExamService(final QuestionService theQuestionService,
                       final AnswerService theAnswerService) {
        this.questionService = theQuestionService;
        this.answerService = theAnswerService;
    }

    /**
     * Creates an Exam.
     * @param questions
     * @return exam
     */
    public Exam create(final Question[] questions) {
        return null;
    }

    /**
     * List Exam Questions.
     * @param examId
     * @return questions
     */
    public Question[] list(final UUID examId) {
        return null;
    }

    /**
     * checks whether the given answer is correct.returns true if correct.
     *
     * @param questionId the question id
     * @param answers     the answers
     * @return true boolean
     */
    public final boolean answer(final UUID questionId,
                                final String[] answers) {
        return false;
    }




}
