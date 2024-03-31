package com.javarush.kojin.cmd;

import com.javarush.kojin.entity.Quest;
import com.javarush.kojin.entity.User;
import com.javarush.kojin.service.AnswerService;
import com.javarush.kojin.service.QuestService;
import com.javarush.kojin.service.QuestionService;
import com.javarush.kojin.service.UserService;
import com.javarush.kojin.util.Go;
import com.javarush.kojin.util.Key;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

public class DeleteQuest implements Command{
    private final QuestService questService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;

    public DeleteQuest(QuestService questService, QuestionService questionService, AnswerService answerService, UserService userService) {
        this.questService = questService;
        this.questionService = questionService;
        this.answerService = answerService;
        this.userService = userService;
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Quest quest = getQuestFromRepoById(req, questService);

        if(quest == null){
            errorNotFound(req, resp);
            return;
        }

        req.setAttribute(Key.QUEST, quest);
        RequestDispatcher requestDispatcherDeleteQuestJsp = req.getRequestDispatcher(Key.FORMAT_LINK_ID.formatted(Go.DELETE_QUEST_JSP, quest.getId()));
        requestDispatcherDeleteQuestJsp.forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Quest quest = getQuestFromRepoById(req, questService);

        if(quest == null){
            errorNotFound(req, resp);
            return;
        }

        if(req.getParameter(Go.DELETE_QUEST) != null){

            quest.getQuestions()
                    .forEach(q -> q.getAnswers()
                            .forEach(a -> answerService.deleteAnswer(a.getId())));

            quest.getQuestions()
                    .forEach(q -> questionService.deleteQuestion(q.getId()));

            Optional<User> optionalAuthor = userService.getUser(quest.getAuthorId());
            if(optionalAuthor.isPresent()){
                User author = optionalAuthor.get();
                author.setUserQuests(
                        author.getUserQuests()
                                .stream()
                                .filter(q -> !q.getId().equals(quest.getId()))
                                .collect(Collectors.toList()));

            }
            questService.deleteQuest(quest.getId());

        }
        resp.sendRedirect(Go.HOME);
    }
}
