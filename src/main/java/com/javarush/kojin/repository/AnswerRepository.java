package com.javarush.kojin.repository;

import com.javarush.kojin.entity.Answer;

import java.util.stream.Stream;

public class AnswerRepository extends BaseRepository<Answer> {
    @Override
    public Stream<Answer> find(Answer pattern) {
        return map.values()
                .stream()
                .filter(a -> nullOrEquals(pattern.getId(), a.getId()))
                .filter(a -> nullOrEquals(pattern.getQuestionId(), a.getQuestionId()))
                .filter(a -> nullOrEquals(pattern.getNextQuestionId(), a.getNextQuestionId()))
                .filter(a -> nullOrEquals(pattern.getText(), a.getText()));
    }
}
