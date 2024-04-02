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


        Quest task = Quest.builder()
                .authorId(admin.getId())
                .isDraft(false)
                .questions(new ArrayList<>())
                .name("Ынопланетный")
                .build();
        questService.createQuest(task);
        admin.getUserQuests().add(task);

        Question taskOne = Question.builder()
                .name("Начало")
                .gameState(GameState.PLAY)
                .questId(task.getId())
                .text("Ты потерял память. Принять вызов НЛО?")
                .answers(new ArrayList<>())
                .build();
        questionService.createQuestion(taskOne);
        task.setStartQuestionId(taskOne.getId());
        task.getQuestions().add(taskOne);


        Question taskTwo = Question.builder()
                .name("И что я тут забыл?!")
                .gameState(GameState.LOSE)
                .questId(task.getId())
                .text("Ты отклонил вызов. Поражение.")
                .answers(new ArrayList<>())
                .build();
        questionService.createQuestion(taskTwo);
        task.getQuestions().add(taskTwo);

        Question taskThree = Question.builder()
                .name("Крымский мостик кЭпа")
                .gameState(GameState.PLAY)
                .questId(task.getId())
                .text("Ты принял вызов. Поднимаешься на мостик к капитану?")
                .answers(new ArrayList<>())
                .build();
        questionService.createQuestion(taskThree);
        task.getQuestions().add(taskThree);

        Answer startOne = Answer.builder()
                .text("Принять вызов")
                .nextQuestionId(taskThree.getId())
                .questionId(taskOne.getId())
                .build();
        Answer startTwo = Answer.builder()
                .text("Отклонить вызов")
                .nextQuestionId(taskTwo.getId())
                .questionId(taskOne.getId())
                .build();
        answerService.createAnswer(startOne);
        answerService.createAnswer(startTwo);

        taskOne.getAnswers().add(startOne);
        taskOne.getAnswers().add(startTwo);

        Question taskFour = Question.builder()
                .name("Не ходите дети в НЛО гулять")
                .gameState(GameState.LOSE)
                .questId(task.getId())
                .text("Ты отказался подниматься на мостик. Поражение.")
                .answers(new ArrayList<>())
                .build();
        questionService.createQuestion(taskFour);
        task.getQuestions().add(taskFour);

        Question taskFive = Question.builder()
                .name("Ты кто такой? Давай приседания!")
                .gameState(GameState.PLAY)
                .questId(task.getId())
                .text("Ты поднялся на мостик. Ты кто?")
                .answers(new ArrayList<>())
                .build();
        questionService.createQuestion(taskFive);
        task.getQuestions().add(taskFive);

        Answer comeOnToMaineShishkaOne = Answer.builder()
                .text("Подняться на мостик")
                .nextQuestionId(taskFive.getId())
                .questionId(taskThree.getId())
                .build();

        Answer comeOnToMaineShishkaTwo = Answer.builder()
                .text("Отказаться подниматься на мостик")
                .nextQuestionId(taskFour.getId())
                .questionId(taskThree.getId())
                .build();

        answerService.createAnswer(comeOnToMaineShishkaOne);
        answerService.createAnswer(comeOnToMaineShishkaTwo);

        taskThree.getAnswers().add(comeOnToMaineShishkaOne);
        taskThree.getAnswers().add(comeOnToMaineShishkaTwo);


        Question taskSix = Question.builder()
                .name("Не 3.14зди")
                .gameState(GameState.LOSE)
                .questId(task.getId())
                .text("Твою ложь разоблачили. Поражение.")
                .answers(new ArrayList<>())
                .build();
        questionService.createQuestion(taskSix);
        task.getQuestions().add(taskSix);

        Question taskSeven = Question.builder()
                .name("Возвращение блудного попугая")
                .gameState(GameState.WIN)
                .questId(task.getId())
                .text("Тебя вернули домой. Победа.")
                .answers(new ArrayList<>())
                .build();
        questionService.createQuestion(taskSeven);
        task.getQuestions().add(taskSeven);

        Answer whoAreYouOne = Answer.builder()
                .text("Рассказать о себе правду")
                .nextQuestionId(taskSeven.getId())
                .questionId(taskFive.getId())
                .build();

        Answer whoAreYouTwo = Answer.builder()
                .text("Солгать о себе")
                .nextQuestionId(taskSix.getId())
                .questionId(taskFive.getId())
                .build();

        answerService.createAnswer(whoAreYouOne);
        answerService.createAnswer(whoAreYouTwo);

        taskFive.getAnswers().add(whoAreYouOne);
        taskFive.getAnswers().add(whoAreYouTwo);
    }

}
