package com.javarush.kojin.repository;

import com.javarush.kojin.entity.Game;

import java.util.stream.Stream;

public class GameRepository extends BaseRepository<Game> {
    @Override
    public Stream<Game> find(Game pattern) {
        return map.values()
                .stream()
                .filter(g -> nullOrEquals(pattern.getId(), g.getId()))
                .filter(g -> nullOrEquals(pattern.getQuestId(), g.getQuestId()))
                .filter(g -> nullOrEquals(pattern.getUserId(), g.getUserId()))
                .filter(g -> nullOrEquals(pattern.getGameState(), g.getGameState()))
                .filter(g -> nullOrEquals(pattern.getCurrentQuestionId(), g.getCurrentQuestionId()));
    }
}
