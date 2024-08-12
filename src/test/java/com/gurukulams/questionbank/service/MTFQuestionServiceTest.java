package com.gurukulams.questionbank.service;

import com.gurukulams.questionbank.model.QuestionChoice;
import com.gurukulams.questionbank.payload.Question;
import com.gurukulams.questionbank.payload.QuestionType;
import com.gurukulams.questionbank.util.TestUtil;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.gurukulams.questionbank.service.QuestionService.OWNER_USER;

class MTFQuestionServiceTest {

    private final QuestionService questionService;

    private final AnswerService answerService;

    MTFQuestionServiceTest() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

        Validator validator = validatorFactory.getValidator();
        this.questionService = new QuestionService(
                validator,
                TestUtil.questionBankManager());
        this.answerService = new AnswerService(this.questionService);
    }
    
    /**
     * Before.
     *
     * @throws IOException the io exception
     */
    @BeforeEach
    void before() throws IOException, SQLException {
        cleanUp();
    }

    /**
     * After.
     */
    @AfterEach
    void after() throws SQLException {
        cleanUp();
    }

    private void cleanUp() throws SQLException {
        questionService.delete();
    }

    @Test
    void testCreateWtihExtraMatch() throws SQLException {
        Question question = newMTFWithExtraMatch();
        Optional<Question> optionalQuestion = this.questionService.create(
                List.of("c1", "c2"),
                null,
                QuestionType.MATCH_THE_FOLLOWING,
                null,
                OWNER_USER,
                question
        );
        Assertions.assertTrue(optionalQuestion.isPresent());
        Assertions.assertEquals(question.getChoices().size(), optionalQuestion.get().getChoices().size());
        Assertions.assertEquals(question.getMatches().size(), optionalQuestion.get().getMatches().size());
        int i=0;
        for(QuestionChoice choice:question.getChoices()) {
            if("Java".equals(choice.getCValue())) {
                Assertions.assertEquals("Object Oriented", optionalQuestion.get().getMatches().get(i).getCValue());
            } else if("Postgres".equals(choice.getCValue())) {
                Assertions.assertEquals("Relational Database", optionalQuestion.get().getMatches().get(i).getCValue());
            } else if("MongoDB".equals(choice.getCValue())) {
                Assertions.assertEquals("Document Database", optionalQuestion.get().getMatches().get(i).getCValue());
            } else if("C".equals(choice.getCValue())) {
                Assertions.assertEquals("System Language", optionalQuestion.get().getMatches().get(i).getCValue());
            } else if(choice.getCValue() == null) {
                Assertions.assertEquals("Extra Match", optionalQuestion.get().getMatches().get(i).getCValue());
            }
            i++;
        }
    }

    @Test
    void testCreateWtihExactMatch() throws SQLException {
        Question question = getQuestionExactMatches();
        Optional<Question> optionalQuestion = this.questionService.create(
                List.of("Computer Science", "Programming"),
                null,
                QuestionType.MATCH_THE_FOLLOWING,
                null,
                OWNER_USER,
                question
        );
        Assertions.assertTrue(optionalQuestion.isPresent());
        Assertions.assertEquals(question.getChoices().size(), optionalQuestion.get().getChoices().size());
        Assertions.assertEquals(question.getMatches().size(), optionalQuestion.get().getMatches().size());
        Assertions.assertEquals(optionalQuestion.get().getChoices().size(), optionalQuestion.get().getMatches().size());
        int i=0;
        for(QuestionChoice choice:question.getChoices()) {
            if("Java".equals(choice.getCValue())) {
                Assertions.assertEquals("Object Oriented", optionalQuestion.get().getMatches().get(i).getCValue());
            } else if("Postgres".equals(choice.getCValue())) {
                Assertions.assertEquals("Relational Database", optionalQuestion.get().getMatches().get(i).getCValue());
            } else if("MongoDB".equals(choice.getCValue())) {
                Assertions.assertEquals("Document Database", optionalQuestion.get().getMatches().get(i).getCValue());
            } else if("C".equals(choice.getCValue())) {
                Assertions.assertEquals("System Language", optionalQuestion.get().getMatches().get(i).getCValue());
            }
            i++;
        }
    }

    Question newMTFWithExtraMatch() {
        Question question = getQuestionExactMatches();
        QuestionChoice choice = new QuestionChoice();
        choice.setCValue("Extra Match");
        question.getMatches().add(choice);
        return question;
    }

    private static Question getQuestionExactMatches() {
        Question question = new Question();
        question.setQuestion("Match the Following");
        question.setExplanation("A Match the Following question");
        question.setChoices(new ArrayList<>());

        QuestionChoice choice = new QuestionChoice();
        choice.setCValue("Java");
        question.getChoices().add(choice);

        choice = new QuestionChoice();
        choice.setCValue("Postgres");
        question.getChoices().add(choice);

        choice = new QuestionChoice();
        choice.setCValue("MongoDB");
        question.getChoices().add(choice);

        choice = new QuestionChoice();
        choice.setCValue("C");
        question.getChoices().add(choice);

        question.setMatches(new ArrayList<>());

        choice = new QuestionChoice();
        choice.setCValue("Object Oriented");
        question.getMatches().add(choice);

        choice = new QuestionChoice();
        choice.setCValue("Relational Database");
        question.getMatches().add(choice);

        choice = new QuestionChoice();
        choice.setCValue("Document Database");
        question.getMatches().add(choice);

        choice = new QuestionChoice();
        choice.setCValue("System Language");
        question.getMatches().add(choice);
        return question;

    }
}