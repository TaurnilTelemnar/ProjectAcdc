package com.javarush.kojin.config;

import com.javarush.kojin.entity.*;
import com.javarush.kojin.service.AnswerService;
import com.javarush.kojin.service.QuestService;
import com.javarush.kojin.service.QuestionService;
import com.javarush.kojin.service.UserService;

import java.util.ArrayList;

public class Config {
    private final UserService userService;
    private final QuestService questService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    public Config(UserService userService, QuestService questService, QuestionService questionService, AnswerService answerService) {
        this.userService = userService;
        this.questService = questService;
        this.questionService = questionService;
        this.answerService = answerService;
    }

    public void addStartData(){
        User admin = User.builder()
                .login("admin")
                .password("admin")
                .name("Admin")
                .role(Role.ADMIN)
                .userQuests(new ArrayList<>())
                .userGames(new ArrayList<>())
                .build();
        User user = User.builder()
                .login("user")
                .password("user")
                .name("User")
                .role(Role.USER)
                .userQuests(new ArrayList<>())
                .userGames(new ArrayList<>())
                .build();
        userService.createUser(admin);
        userService.createUser(user);

        Quest quest = Quest.builder()
                .authorId(admin.getId())
                .isDraft(false)
                .questions(new ArrayList<>())
                .name("Тестовый квест")
                .build();
        questService.createQuest(quest);

        admin.getUserQuests().add(quest);

        Question questionOne = Question.builder()
                .name("Вопрос №1")
                .text("Куда ты хочешь пойти?")
                .gameState(GameState.PLAY)
                .questId(quest.getId())
                .answers(new ArrayList<>())
                .build();

        Question questionTwo = Question.builder()
                .name("Вопрос №2")
                .text("Ты проиграл!")
                .gameState(GameState.LOSE)
                .questId(quest.getId())
                .answers(new ArrayList<>())
                .build();

        Question questionThree = Question.builder()
                .name("Вопрос №3")
                .text("Ты выиграл!")
                .gameState(GameState.WIN)
                .questId(quest.getId())
                .answers(new ArrayList<>())
                .build();

        questionService.createQuestion(questionOne);
        questionService.createQuestion(questionTwo);
        questionService.createQuestion(questionThree);

        quest.setStartQuestionId(questionOne.getId());

        quest.getQuestions().add(questionOne);
        quest.getQuestions().add(questionTwo);
        quest.getQuestions().add(questionThree);

        Answer answerOne = Answer.builder()
                .text("К Вопросу №2?")
                .questionId(questionOne.getId())
                .nextQuestionId(questionTwo.getId())
                .build();


        Answer answerTwo = Answer.builder()
                .text("К Вопросу №3?")
                .questionId(questionOne.getId())
                .nextQuestionId(questionThree.getId())
                .build();

        answerService.createAnswer(answerOne);
        answerService.createAnswer(answerTwo);

        questionOne.getAnswers().add(answerOne);
        questionOne.getAnswers().add(answerTwo);

    }

}
