package com.javarush.kojin.service;

import com.javarush.kojin.entity.Question;
import com.javarush.kojin.repository.QuestionRepository;

public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void createQuestion(Question question) {
        questionRepository.create(question);
    }

    public void updateQuestion(Question question){
        questionRepository.update(question);
    }

    public Question getQuestion(Long questionId) {
        return questionRepository.get(questionId);
    }

    public void deleteQuestion(Long questionId) {
        questionRepository.delete(questionId);
    }
}
