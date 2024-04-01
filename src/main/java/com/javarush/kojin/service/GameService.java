package com.javarush.kojin.service;

import com.javarush.kojin.entity.Game;
import com.javarush.kojin.repository.GameRepository;

public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void createGame(Game game) {
        gameRepository.create(game);
    }
}
