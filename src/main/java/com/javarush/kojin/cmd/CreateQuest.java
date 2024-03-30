package com.javarush.kojin.cmd;

import com.javarush.kojin.entity.*;
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
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

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
            Question question = questionService.getQuestion(Long.parseLong(req.getParameter(Key.QUESTION_ID)));

            question.setName(req.getParameter(Key.QUESTION_NAME));
            question.setText(req.getParameter(Key.QUESTION_TEXT));

            GameState questionGameState = GameState.valueOf(req.getParameter(Key.QUESTION_GAME_STATE));
            question.setGameState(questionGameState);
        }


        if (req.getParameter(Key.ADD_QUESTION) != null) {
            Question question = Question.builder()
                    .questId(draft.getId())
                    .answers(new ArrayList<>())
                    .build();
            draft.getQuestions().add(question);
            questionService.createQuestion(question);
        }

        if (req.getParameter(Key.DELETE_QUESTION) != null) {
            Long questionId = Long.parseLong(req.getParameter(Key.QUESTION_ID));
            draft.setQuestions(
                    draft.getQuestions()
                            .stream()
                            .filter(q -> !q.getId().equals(questionId))
                            .collect(Collectors.toList()));
            questionService.deleteQuestion(questionId);
        }

        if (req.getParameter(Key.ADD_ANSWER) != null) {
            Long questionId = Long.parseLong(req.getParameter(Key.QUESTION_ID));
            Question question = questionService.getQuestion(questionId);

            Answer answer = Answer.builder()
                    .questionId(questionId)
                    .build();

            answerService.createAnswer(answer);

            question.getAnswers().add(answer);
        }

        if (req.getParameter(Key.SAVE_ANSWER) != null) {
            Long answerId = Long.parseLong(req.getParameter(Key.ANSWER_ID));
            Answer answer = answerService.getAnswer(answerId);

            answer.setText(req.getParameter(Key.ANSWER_TEXT));
            Long nextQuestionId =
                    req.getParameter(Key.ANSWER_NEXT_QUESTION_ID) != null
                            ? Long.parseLong(req.getParameter(Key.ANSWER_NEXT_QUESTION_ID))
                            : null;
            answer.setNextQuestionId(nextQuestionId);
        }

        if (req.getParameter(Key.DELETE_ANSWER) != null) {
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

        if (req.getParameter(Key.SAVE_QUEST_PARAMS) != null) {
            draft.setName(req.getParameter(Key.QUEST_NAME));
            System.out.println(req.getParameter(Key.QUEST_START_QUESTION_ID));
            System.out.println(req.getParameter("questStartQuestionName"));
            Long startQuestionId =
                    req.getParameter(Key.QUEST_START_QUESTION_ID) != null
                            ? Long.parseLong(req.getParameter(Key.QUEST_START_QUESTION_ID))
                            : null;
            draft.setStartQuestionId(startQuestionId);
        }


        resp.sendRedirect(Go.CREATE_QUEST);
    }

}
