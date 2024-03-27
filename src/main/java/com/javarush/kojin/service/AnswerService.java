package com.javarush.kojin.service;

import com.javarush.kojin.repository.AnswerRepository;

public class AnswerService {
    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }
}
