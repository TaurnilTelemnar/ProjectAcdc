package com.javarush.kojin.cmd;

import com.javarush.kojin.entity.Quest;
import com.javarush.kojin.service.AnswerService;
import com.javarush.kojin.service.QuestService;
import com.javarush.kojin.service.QuestionService;
import com.javarush.kojin.util.Go;
import com.javarush.kojin.util.Key;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class EditQuest implements Command{
    private final QuestService questService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    public EditQuest(QuestService questService, QuestionService questionService, AnswerService answerService) {
        this.questService = questService;
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Quest quest = getQuestFromRepoById(req, questService);
        if(quest == null){
            errorNotFound(req, resp);
            return;
        }

        req.setAttribute(Key.QUEST, quest);
        RequestDispatcher requestDispatcherEditQuestJsp = req.getRequestDispatcher(Key.FORMAT_LINK_ID.formatted(Go.EDIT_QUEST_JSP, quest.getId()));
        requestDispatcherEditQuestJsp.forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Quest quest = getQuestFromRepoById(req, questService);
        if(quest == null){
            errorNotFound(req, resp);
            return;
        }

        if (req.getParameter(Key.SAVE_QUESTION) != null) {
            saveQuestion(questionService, req);
        }else if (req.getParameter(Key.ADD_QUESTION) != null) {
            addQuestion(questionService, quest);
        }else if (req.getParameter(Key.DELETE_QUESTION) != null) {
            deleteQuestion(questionService, req, quest);
        }else if (req.getParameter(Key.ADD_ANSWER) != null) {
            addAnswer(questionService, answerService, req);
        }else if (req.getParameter(Key.SAVE_ANSWER) != null) {
            saveAnswer(answerService, req);
        }else if (req.getParameter(Key.DELETE_ANSWER) != null) {
            deleteAnswer(questionService, answerService, req);
        }else if (req.getParameter(Key.SAVE_QUEST_PARAMS) != null) {
            saveQuestParams(quest, req);
        }

        resp.sendRedirect(Key.FORMAT_LINK_ID.formatted(Go.EDIT_QUEST, quest.getId()));
    }
}
