package com.javarush.kojin.cmd;

import com.javarush.kojin.service.AnswerService;
import com.javarush.kojin.service.QuestService;
import com.javarush.kojin.service.QuestionService;
import com.javarush.kojin.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CreateQuest implements Command{
    private final QuestService questService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;

    public CreateQuest(QuestService questService, QuestionService questionService, AnswerService answerService, UserService userService) {
        this.questService = questService;
        this.questionService = questionService;
        this.answerService = answerService;
        this.userService = userService;
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //ToDo тут нужно создать какой-то тестовый квест с парой страниц и несколькими вопросами для этих страниц и сделать его черновиком
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
