package com.javarush.kojin.cmd;

import com.javarush.kojin.entity.*;
import com.javarush.kojin.service.AnswerService;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class CreateQuest implements Command {
    private final QuestService questService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    public CreateQuest(QuestService questService, QuestionService questionService, AnswerService answerService) {
        this.questService = questService;
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(Key.USER);

        Collection<Quest> userQuests = user.getUserQuests();
        Optional<Quest> optionalDraft = userQuests.stream()
                .filter(Quest::getIsDraft)
                .findFirst();

        Quest draft = optionalDraft.orElse(null);
        if (optionalDraft.isEmpty()) {
            draft = Quest.builder()
                    .authorId(user.getId())
                    .isDraft(true)
                    .questions(new ArrayList<>())
                    .build();
            questService.createQuest(draft);
            user.getUserQuests().add(draft);
        }
        session.setAttribute(Key.QUEST_DRAFT, draft);
        RequestDispatcher requestDispatcherCreateQuestJsp = req.getRequestDispatcher(Go.CREATE_QUEST_JSP);
        requestDispatcherCreateQuestJsp.forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Quest draft = (Quest) session.getAttribute(Key.QUEST_DRAFT);

        if (req.getParameter(Key.SAVE_QUESTION) != null) {
            saveQuestion(questionService, req);
        }else if (req.getParameter(Key.ADD_QUESTION) != null) {
            addQuestion(questionService, draft);
        }else if (req.getParameter(Key.DELETE_QUESTION) != null) {
            deleteQuestion(questionService, req, draft);
        }else if (req.getParameter(Key.ADD_ANSWER) != null) {
            addAnswer(questionService, answerService, req);
        }else if (req.getParameter(Key.SAVE_ANSWER) != null) {
            saveAnswer(answerService, req);
        }else if (req.getParameter(Key.DELETE_ANSWER) != null) {
            deleteAnswer(questionService, answerService, req);
        }else if (req.getParameter(Key.SAVE_QUEST_PARAMS) != null) {
            saveQuestParams(draft, req);
        }else if(req.getParameter(Key.PUBLISH_QUEST) != null){
            session.removeAttribute(Key.QUEST_DRAFT);
            draft.setIsDraft(false);
            resp.sendRedirect(Key.FORMAT_LINK_ID.formatted(Go.PLAY, draft.getId()));
            return;
        }


        resp.sendRedirect(Go.CREATE_QUEST);
    }

}
