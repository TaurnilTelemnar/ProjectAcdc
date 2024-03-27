package com.javarush.kojin.repository;

import com.javarush.kojin.entity.Quest;

import java.util.stream.Stream;

public class QuestRepository extends BaseRepository<Quest> {
    @Override
    public Stream<Quest> find(Quest pattern) {
        return map.values()
                .stream()
                .filter(q -> nullOrEquals(pattern.getId(), q.getId()))
                .filter(q -> nullOrEquals(pattern.getName(), q.getName()))
                .filter(q -> nullOrEquals(pattern.getAuthorId(), q.getAuthorId()))
                .filter(q -> nullOrEquals(pattern.getStartQuestionId(), q.getStartQuestionId()))
                .filter(q -> nullOrEquals(pattern.getQuestions(), q.getQuestions()))
                .filter(q -> nullOrEquals(pattern.getIsDraft(), q.getIsDraft()))
                .filter(q -> nullOrEquals(pattern.getImageId(), q.getImageId()));
    }
}
