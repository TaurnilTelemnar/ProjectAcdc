package com.javarush.kojin.repository;

import com.javarush.kojin.entity.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class QuestionRepositoryTest {
    QuestionRepository questionRepository;
    Question question;

    @BeforeEach
    void init() {
        questionRepository = new QuestionRepository();
        question = Question.builder()
                .name("Вопрос")
                .build();
        questionRepository.create(question);
    }

    @Test
    void whenQuestionFindByNameThenReturnsQuestionFromRepo() {
        Question pattern = Question.builder()
                .name("Вопрос")
                .build();

        Optional<Question> testObject = questionRepository.find(pattern).findFirst();
        assertTrue(testObject.isPresent());
        String questionName = question.getName();
        String testQuestionName = testObject.get().getName();
        assertEquals(questionName, testQuestionName);
    }

    @Test
    void whenCreateQuestionThenQuestionFromRepoHasId() {
        Question testQuestion = Question.builder()
                .name("newQuestion")
                .build();
        questionRepository.create(testQuestion);
        assertNotNull(testQuestion.getId());
    }

    @Test
    void whenGetQuestionFromRepoThenReturnsCorrectQuestion() {
        Long questionId = question.getId();

        Question testQuestion = questionRepository.get(questionId);
        assertEquals(question, testQuestion);
    }

    @Test
    void whenGetAllQuestionsThenTakenQuestionsAreMatches(){
        Collection<Question> allQuestions = questionRepository.getAll();
        assertTrue(allQuestions.contains(question));
    }

    @Test
    void whenDeleteQuestionsThenTakenNull() {
        Long questionId = question.getId();
        questionRepository.delete(questionId);

        Question testQuestion = questionRepository.get(questionId);
        assertNull(testQuestion);
    }
}