package com.javarush.kojin.cmd;

import com.javarush.kojin.entity.*;
import com.javarush.kojin.service.GameService;
import com.javarush.kojin.service.QuestService;
import com.javarush.kojin.service.QuestionService;
import com.javarush.kojin.util.Go;
import com.javarush.kojin.util.Key;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

public class Play implements Command {
    private final GameService gameService;
    private final QuestService questService;
    private final QuestionService questionService;

    public Play(GameService gameService, QuestService questService, QuestionService questionService) {
        this.gameService = gameService;
        this.questService = questService;
        this.questionService = questionService;
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Quest userQuest = getQuest(req, resp);
        if(userQuest == null){
            return;
        }

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(Key.USER);


        Optional<Game> optionalGame = getOptionalGame(user, userQuest);
        Game userGame = optionalGame.orElseGet(() -> createNewGame(user, userQuest));
        Question question = questionService.getQuestion(userGame.getCurrentQuestionId());
        userGame.setGameState(question.getGameState());
        req.setAttribute(Key.GAME, userGame);
        req.setAttribute(Key.QUESTION, question);
        req.setAttribute(Key.QUEST, userQuest);

        RequestDispatcher requestDispatcherPlayJsp = req.getRequestDispatcher(Key.FORMAT_LINK_ID.formatted(Go.PLAY_JSP, userQuest.getId()));
        requestDispatcherPlayJsp.forward(req, resp);
    }


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Quest userQuest = getQuest(req, resp);

        if(userQuest == null){
            return;
        }

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(Key.USER);
        Optional<Game> optionalGame = user.getUserGames()
                .stream()
                .filter(g -> g.getQuestId().equals(userQuest.getId()))
                .filter(g -> g.getGameState().equals(GameState.PLAY))
                .findFirst();

        if(optionalGame.isEmpty()){
            resp.sendRedirect(Key.FORMAT_LINK_ID.formatted(Go.PLAY, userQuest.getId()));
            return;
        }

        Game userGame = optionalGame.get();

        if(req.getParameter(Go.PLAY) != null){
            Long answerNextQuestionId = Long.parseLong(req.getParameter(Key.ANSWER_NEXT_QUESTION_ID));
            userGame.setCurrentQuestionId(answerNextQuestionId);
            resp.sendRedirect(Key.FORMAT_LINK_ID.formatted(Go.PLAY, userQuest.getId()));
        }else if(req.getParameter(Key.NEW_GAME) != null){
            resp.sendRedirect(Key.FORMAT_LINK_ID.formatted(Go.PLAY, userQuest.getId()));
        } else {
            errorNotFound(req, resp);
        }
    }

    private Game createNewGame(User user, Quest userQuest) {
        Game userGame = Game.builder()
                .userId(user.getId())
                .gameState(GameState.PLAY)
                .questId(userQuest.getId())
                .currentQuestionId(userQuest.getStartQuestionId())
                .build();
        gameService.createGame(userGame);
        user.getUserGames().add(userGame);
        return userGame;
    }

    private Quest getQuest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long questId = getIdFromRequest(req);
        Optional<Quest> optionalQuest = questService.getQuest(questId);

        if (optionalQuest.isEmpty()) {
            errorNotFound(req, resp);
            return null;
        }

        return optionalQuest.get();
    }

    private Optional<Game> getOptionalGame(User user, Quest userQuest){
        return user.getUserGames()
                .stream()
                .filter(g -> g.getQuestId().equals(userQuest.getId()))
                .filter(g -> g.getGameState().equals(GameState.PLAY))
                .findFirst();

    }
}
