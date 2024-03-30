package com.javarush.kojin.service;

import com.javarush.kojin.entity.Answer;
import com.javarush.kojin.repository.AnswerRepository;

public class AnswerService {
    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public void createAnswer(Answer answer) {
        answerRepository.create(answer);
    }

    public void updateAnswer(Answer answer) {
        answerRepository.update(answer);
    }

    public Answer getAnswer(Long answerId) {
        return answerRepository.get(answerId);
    }

    public void deleteAnswer(Long answerId) {
        answerRepository.delete(answerId);
    }
}
