package com.javarush.kojin.repository;

import com.javarush.kojin.entity.Question;

import java.util.stream.Stream;

public class QuestionRepository extends BaseRepository<Question> {
    @Override
    public Stream<Question> find(Question pattern) {
        return map.values()
                .stream()
                .filter(q -> nullOrEquals(pattern.getId(),q.getId()))
                .filter(q -> nullOrEquals(pattern.getQuestId(),q.getQuestId()))
                .filter(q -> nullOrEquals(pattern.getAnswers(),q.getAnswers()))
                .filter(q -> nullOrEquals(pattern.getName(),q.getName()))
                .filter(q -> nullOrEquals(pattern.getText(),q.getText()))
                .filter(q -> nullOrEquals(pattern.getGameState(),q.getGameState()));
    }
}
