package com.javarush.kojin.cmd;

import com.javarush.kojin.entity.*;
import com.javarush.kojin.service.AnswerService;
import com.javarush.kojin.service.QuestService;
import com.javarush.kojin.service.QuestionService;
import com.javarush.kojin.service.UserService;
import com.javarush.kojin.util.Go;
import com.javarush.kojin.util.Key;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public interface Command {

    void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;


    void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

    default void errorUserAlreadyExists(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        session.setAttribute(Key.ERROR, Key.ERROR_USER_ALREADY_EXISTS);
        resp.sendRedirect(Go.SIGNUP);
    }

    default User getUserFromRequest(HttpServletRequest req, UserService userService) {
        User pattern = User.builder()
                .login(req.getParameter(Key.USER_LOGIN))
                .password(req.getParameter(Key.USER_PASSWORD))
                .build();
        Optional<User> optionalUser = userService.getUser(pattern);
        return optionalUser.orElse(null);
    }


    default void errorNotFound(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        session.setAttribute(Key.ERROR, Key.ERROR_404);
        resp.sendRedirect(Go.ERROR);
    }


    default boolean isUserLoginAlreadyExists(String userLogin, UserService userService) throws IOException {
        User pattern = User.builder()
                .login(userLogin)
                .build();
        return userService.getUser(pattern).isPresent();
    }

    default Long getIdFromRequest(HttpServletRequest req){
        String stringId = req.getParameter(Key.ID);
        return stringId != null
                ? Long.parseLong(stringId)
                : null;
    }

    default boolean isIdFromRequestNotExists(HttpServletRequest req){
        return getIdFromRequest(req) == null;
    }

    default User getUserFromRepoById(HttpServletRequest req, UserService userService){
        if(isIdFromRequestNotExists(req)){
            return null;
        }
        Optional<User> optionalUser = userService.getUser(getIdFromRequest(req));
        return optionalUser.orElse(null);
    }

    default Quest getQuestFromRepoById(HttpServletRequest req, QuestService questService){
        if(isIdFromRequestNotExists(req)){
            return null;
        }
        Optional<Quest> optionalQuest = questService.getQuest(getIdFromRequest(req));
        return optionalQuest.orElse(null);
    }

    default void saveQuestion(QuestionService questionService, HttpServletRequest req){
        Question question = questionService.getQuestion(Long.parseLong(req.getParameter(Key.QUESTION_ID)));

        question.setName(req.getParameter(Key.QUESTION_NAME));
        question.setText(req.getParameter(Key.QUESTION_TEXT));

        GameState questionGameState = GameState.valueOf(req.getParameter(Key.QUESTION_GAME_STATE));
        question.setGameState(questionGameState);
    }

    default void addQuestion(QuestionService questionService, Quest quest){
        Question question = Question.builder()
                .questId(quest.getId())
                .answers(new ArrayList<>())
                .build();
        quest.getQuestions().add(question);
        questionService.createQuestion(question);
    }

    default void deleteQuestion(QuestionService questionService, HttpServletRequest req, Quest quest){
        Long questionId = Long.parseLong(req.getParameter(Key.QUESTION_ID));
        quest.setQuestions(
                quest.getQuestions()
                        .stream()
                        .filter(q -> !q.getId().equals(questionId))
                        .collect(Collectors.toList()));
        questionService.deleteQuestion(questionId);
    }

    default void addAnswer(QuestionService questionService, AnswerService answerService, HttpServletRequest req){
        Long questionId = Long.parseLong(req.getParameter(Key.QUESTION_ID));
        Question question = questionService.getQuestion(questionId);

        Answer answer = Answer.builder()
                .questionId(questionId)
                .build();

        answerService.createAnswer(answer);

        question.getAnswers().add(answer);
    }

    default void saveAnswer(AnswerService answerService, HttpServletRequest req){
        Long answerId = Long.parseLong(req.getParameter(Key.ANSWER_ID));
        Answer answer = answerService.getAnswer(answerId);

        answer.setText(req.getParameter(Key.ANSWER_TEXT));
        Long nextQuestionId =
                req.getParameter(Key.ANSWER_NEXT_QUESTION_ID) != null
                        ? Long.parseLong(req.getParameter(Key.ANSWER_NEXT_QUESTION_ID))
                        : null;
        answer.setNextQuestionId(nextQuestionId);
    }

    default void deleteAnswer(QuestionService questionService, AnswerService answerService, HttpServletRequest req){
        Long answerId = Long.parseLong(req.getParameter(Key.ANSWER_ID));
        Long questionId = Long.parseLong(req.getParameter(Key.QUESTION_ID));

        Question question = questionService.getQuestion(questionId);

        question.setAnswers(
                question.getAnswers()
                        .stream()
                        .filter(a -> !a.getId().equals(answerId))
                        .collect(Collectors.toList()));

        answerService.deleteAnswer(answerId);
    }

    default void saveQuestParams(Quest quest, HttpServletRequest req){
        quest.setName(req.getParameter(Key.QUEST_NAME));
        Long startQuestionId =
                req.getParameter(Key.QUEST_START_QUESTION_ID) != null
                        ? Long.parseLong(req.getParameter(Key.QUEST_START_QUESTION_ID))
                        : null;
        quest.setStartQuestionId(startQuestionId);
    }
}
