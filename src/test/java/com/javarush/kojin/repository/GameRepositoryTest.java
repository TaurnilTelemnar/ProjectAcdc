package com.javarush.kojin.repository;

import com.javarush.kojin.entity.Game;
import com.javarush.kojin.entity.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GameRepositoryTest {
    GameRepository gameRepository;
    Game game;

    @BeforeEach
    void init(){
        gameRepository = new GameRepository();
        game = Game.builder()
                .gameState(GameState.PLAY)
                .userId(1L)
                .build();
        gameRepository.create(game);
    }

    @Test
    void whenGameFindByUserIdThenReturnsGameFromRepo() {
        Game pattern = Game.builder()
                .userId(1L)
                .build();

        Optional<Game> testObject = gameRepository.find(pattern).findFirst();
        assertTrue(testObject.isPresent());
        GameState gameState = game.getGameState();
        GameState testGameState = testObject.get().getGameState();
        assertEquals(gameState, testGameState);
    }

    @Test
    void whenCreateGameThenGameFromRepoHasId() {
        Game testGame = Game.builder()
                .gameState(GameState.PLAY)
                .build();
        gameRepository.create(testGame);
        assertNotNull(testGame.getId());
    }

    @Test
    void whenGetGameFromRepoThenReturnsCorrectGame() {
        Long gameId = game.getId();

        Game testGame = gameRepository.get(gameId);
        assertEquals(game, testGame);
    }

    @Test
    void whenGetAllGamesThenTakenGamesAreMatches() {
        Collection<Game> allGames = gameRepository.getAll();
        assertTrue(allGames.contains(game));
    }

    @Test
    void whenDeleteGameThenTakenNull() {
        Long gameId = game.getId();
        gameRepository.delete(gameId);

        Game testGame = gameRepository.get(gameId);
        assertNull(testGame);
    }
}