package com.javarush.kojin.cmd;

import com.javarush.kojin.BaseIT;
import com.javarush.kojin.config.Components;
import com.javarush.kojin.entity.*;
import com.javarush.kojin.repository.GameRepository;
import com.javarush.kojin.repository.QuestRepository;
import com.javarush.kojin.repository.QuestionRepository;
import com.javarush.kojin.repository.UserRepository;
import com.javarush.kojin.util.Go;
import com.javarush.kojin.util.Key;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

class PlayIT extends BaseIT {
    private final Play play = (Play) Components.getCommand(Go.PLAY);

    @Test
    void whenGetPlayWithoutExistingQuest_thenRedirectError() throws ServletException, IOException {
        when(request.getParameter(Key.ID)).thenReturn("113");

        play.doGet(request, response);

        verify(request).getParameter(eq(Key.ID));
        verify(request).getSession();
        verify(session).setAttribute(eq(Key.ERROR), eq(Key.ERROR_404));
        verify(response).sendRedirect(eq(Go.ERROR));
    }

    @Test
    void whenGetPlayWithExistingQuest_thenForwardPlayJsp() throws ServletException, IOException {



        when(request.getParameter(Key.ID)).thenReturn("1");
        User user = Components.getComponent(UserRepository.class).get(1L);

        Game lostUserGame = Game.builder()
                .questId(1L)
                .userId(1L)
                .gameState(GameState.LOSE)
                .build();
        Components.getComponent(GameRepository.class).create(lostUserGame);

        user.getUserGames().add(lostUserGame);

        when(session.getAttribute(Key.USER)).thenReturn(user);
        when(request.getRequestDispatcher(Key.FORMAT_LINK_ID.formatted(Go.PLAY_JSP, 1))).thenReturn(requestDispatcher);

        play.doGet(request, response);

        verify(request).getParameter(eq(Key.ID));
        verify(session).getAttribute(eq(Key.USER));
        verify(request).setAttribute(eq(Key.GAME), any(Game.class));
        verify(request).setAttribute(eq(Key.QUESTION), any(Question.class));
        verify(request).setAttribute(eq(Key.QUEST), any(Quest.class));
        verify(request).getRequestDispatcher(eq(Key.FORMAT_LINK_ID.formatted(Go.PLAY_JSP, 1)));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void whenPostPlayWithoutExistingQuest_thenRedirectError() throws ServletException, IOException {
        when(request.getParameter(Key.ID)).thenReturn("165");

        play.doPost(request, response);

        verify(request).getParameter(eq(Key.ID));
        verify(request).getSession();
        verify(session).setAttribute(eq(Key.ERROR), eq(Key.ERROR_404));
        verify(response).sendRedirect(eq(Go.ERROR));
    }

    @Test
    void whenPostPlayWithExistingQuest_And_WithoutExistingGame_thenRedirectPlay() throws ServletException, IOException {
        when(request.getParameter(Key.ID)).thenReturn("1");
        User user = Components.getComponent(UserRepository.class).get(1L);
        when(session.getAttribute(Key.USER)).thenReturn(user);

        play.doPost(request, response);

        verify(request).getParameter(eq(Key.ID));
        verify(request).getSession();
        verify(session).getAttribute(eq(Key.USER));
        verify(response).sendRedirect(eq(Key.FORMAT_LINK_ID.formatted(Go.PLAY, 1)));
    }

    @Test
    void whenPostPlayWithExistingQuest_And_WithExistingGame_thenRedirectPlay() throws ServletException, IOException {
        when(request.getParameter(Key.ID)).thenReturn("2");
        User user = Components.getComponent(UserRepository.class).get(1L);
        Quest quest = Components.getComponent(QuestRepository.class).get(2L);
        Question firstQuestion = Components.getComponent(QuestionRepository.class).get(quest.getStartQuestionId());
        Answer firstAnswer = firstQuestion.getAnswers().stream().findFirst().get();
        Game userGame = Game.builder()
                .questId(2L)
                .userId(user.getId())
                .currentQuestionId(quest.getStartQuestionId())
                .gameState(GameState.PLAY)
                .build();

        Components.getComponent(GameRepository.class).create(userGame);


        user.getUserGames().add(userGame);
        when(session.getAttribute(Key.USER)).thenReturn(user);
        when(request.getParameter(Go.PLAY)).thenReturn(Go.PLAY);
        when(request.getParameter(Key.ANSWER_NEXT_QUESTION_ID)).thenReturn(String.valueOf(firstAnswer.getNextQuestionId()));

        play.doPost(request, response);

        verify(request).getParameter(eq(Key.ID));
        verify(request).getSession();
        verify(session).getAttribute(eq(Key.USER));
        verify(request).getParameter(eq(Go.PLAY));
        verify(request).getParameter(eq(Key.ANSWER_NEXT_QUESTION_ID));
        verify(response).sendRedirect(eq(Key.FORMAT_LINK_ID.formatted(Go.PLAY, quest.getId())));
    }

    @Test
    void whenPostPlayWithExistingQuest_And_WithExistingGame_And_startNewGame_thenRedirectPlay() throws ServletException, IOException {
        when(request.getParameter(Key.ID)).thenReturn("2");
        User user = Components.getComponent(UserRepository.class).get(1L);
        Quest quest = Components.getComponent(QuestRepository.class).get(2L);
        Question firstQuestion = Components.getComponent(QuestionRepository.class).get(quest.getStartQuestionId());
        Answer firstAnswer = firstQuestion.getAnswers().stream().findFirst().get();
        Game userGame = Game.builder()
                .questId(2L)
                .userId(user.getId())
                .currentQuestionId(quest.getStartQuestionId())
                .gameState(GameState.PLAY)
                .build();
        Components.getComponent(GameRepository.class).create(userGame);
        user.getUserGames().add(userGame);
        when(session.getAttribute(Key.USER)).thenReturn(user);
        when(request.getParameter(Go.PLAY)).thenReturn(null);
        when(request.getParameter(Key.NEW_GAME)).thenReturn(Key.NEW_GAME);

        play.doPost(request, response);

        verify(request).getParameter(eq(Key.ID));
        verify(request).getSession();
        verify(session).getAttribute(eq(Key.USER));
        verify(request).getParameter(eq(Go.PLAY));
        verify(request).getParameter(eq(Key.NEW_GAME));
        verify(response).sendRedirect(eq(Key.FORMAT_LINK_ID.formatted(Go.PLAY, quest.getId())));
    }

    @Test
    void whenPostPlayWithExistingQuest_And_WithExistingGame_And_sendWrongParams_thenRedirectError() throws ServletException, IOException {
        when(request.getParameter(Key.ID)).thenReturn("2");
        User user = Components.getComponent(UserRepository.class).get(1L);
        Quest quest = Components.getComponent(QuestRepository.class).get(2L);

        Game userGame = Game.builder()
                .questId(2L)
                .userId(user.getId())
                .currentQuestionId(quest.getStartQuestionId())
                .gameState(GameState.PLAY)
                .build();
        Components.getComponent(GameRepository.class).create(userGame);

        user.getUserGames().add(userGame);
        when(session.getAttribute(Key.USER)).thenReturn(user);
        when(request.getParameter(Go.PLAY)).thenReturn(null);
        when(request.getParameter(Key.NEW_GAME)).thenReturn(null);

        play.doPost(request, response);

        verify(request).getParameter(eq(Key.ID));
        verify(session).getAttribute(eq(Key.USER));
        verify(request).getParameter(eq(Go.PLAY));
        verify(request).getParameter(eq(Key.NEW_GAME));
        verify(request, times(2)).getSession();
        verify(session).setAttribute(eq(Key.ERROR), eq(Key.ERROR_404));
        verify(response).sendRedirect(eq(Go.ERROR));
    }
}