package com.javarush.kojin.repository;

import com.javarush.kojin.entity.Answer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AnswerRepositoryTest {
    AnswerRepository answerRepository;
    Answer answer;

    @BeforeEach
    void init(){
        answerRepository = new AnswerRepository();
        answer = Answer.builder()
                .text("Согласиться")
                .build();
        answerRepository.create(answer);
    }

    @Test
    void whenAnswerFindByTextThenReturnsAnswerFromRepo() {
        Answer pattern = Answer.builder()
                .text("Согласиться")
                .build();

        Optional<Answer> testObject = answerRepository.find(pattern).findFirst();
        assertTrue(testObject.isPresent());
        String answerText = answer.getText();
        String testAnswerText = testObject.get().getText();
        assertEquals(answerText, testAnswerText);
    }

    @Test
    void whenCreateAnswerThenAnswerFromRepoHasId(){
        Answer testAnswer = Answer.builder()
                .text("testText")
                .build();
        answerRepository.create(testAnswer);
        assertNotNull(testAnswer.getId());
    }
    @Test
    void whenGetAnswerFromRepoThenReturnsCorrectAnswer(){
        Long answerId = answer.getId();

        Answer testAnswer = answerRepository.get(answerId);
        assertEquals(answer, testAnswer);
    }

    @Test
    void whenGetAllAnswersThenTakenAnswersAreMatches(){
        Collection<Answer> allAnswers = answerRepository.getAll();
        assertTrue(allAnswers.contains(answer));
    }

    @Test
    void whenDeleteAnswerFromRepoThenTakenNull(){
        Long answerId = answer.getId();
        answerRepository.delete(answerId);

        Answer testAnswer = answerRepository.get(answerId);
        assertNull(testAnswer);
    }

}