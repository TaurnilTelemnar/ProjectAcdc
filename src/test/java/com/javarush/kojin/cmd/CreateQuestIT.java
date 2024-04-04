package com.javarush.kojin.cmd;

import com.javarush.kojin.BaseIT;
import com.javarush.kojin.config.Components;
import com.javarush.kojin.entity.*;
import com.javarush.kojin.repository.UserRepository;
import com.javarush.kojin.service.AnswerService;
import com.javarush.kojin.service.QuestService;
import com.javarush.kojin.service.QuestionService;
import com.javarush.kojin.util.Go;
import com.javarush.kojin.util.Key;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CreateQuestIT extends BaseIT {
    private final CreateQuest createQuest = (CreateQuest) Components.getCommand(Go.CREATE_QUEST);

    @Test
    void whenUserCreateNewDraft_thenNewDraftCreates() throws ServletException, IOException {
        User user = Components.getComponent(UserRepository.class).get(2L);

        assertTrue(user.getUserQuests().isEmpty());

        when(session.getAttribute(Key.USER)).thenReturn(user);
        doNothing().when(session).setAttribute(Key.QUEST_DRAFT, Quest.class);
        when(request.getRequestDispatcher(Go.CREATE_QUEST_JSP)).thenReturn(requestDispatcher);

        createQuest.doGet(request, response);

        assertFalse(user.getUserQuests().isEmpty());

        verify(session).getAttribute(eq(Key.USER));
        verify(session).setAttribute(eq(Key.QUEST_DRAFT), any(Quest.class));
        verify(request).getRequestDispatcher(eq(Go.CREATE_QUEST_JSP));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void whenUserGetExistingDraft_thenDraftPutsOnRequest() throws ServletException, IOException {
        User user = Components.getComponent(UserRepository.class).get(2L);
        Quest draft = Quest.builder()
                .isDraft(true)
                .build();
        user.getUserQuests().add(draft);
        assertFalse(user.getUserQuests().isEmpty());

        when(session.getAttribute(Key.USER)).thenReturn(user);
        doNothing().when(session).setAttribute(Key.QUEST_DRAFT, Quest.class);
        when(request.getRequestDispatcher(Go.CREATE_QUEST_JSP)).thenReturn(requestDispatcher);

        createQuest.doGet(request, response);

        verify(session).getAttribute(eq(Key.USER));
        verify(session).setAttribute(eq(Key.QUEST_DRAFT), any(Quest.class));
        verify(request).getRequestDispatcher(eq(Go.CREATE_QUEST_JSP));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void whenUserSaveQuestion_thenQuestionSaved() throws IOException, ServletException {
        Question draftQuestion = Question.builder()
                .name("Question")
                .gameState(GameState.PLAY)
                .build();
        Quest draft = Quest.builder()
                .isDraft(true)
                .questions(new ArrayList<>())
                .build();
        Components.getComponent(QuestionService.class).createQuestion(draftQuestion);
        Components.getComponent(QuestService.class).createQuest(draft);
        draft.getQuestions().add(draftQuestion);
        assertNotNull(draftQuestion.getId());
        assertNotNull(draft.getId());

        when(session.getAttribute(Key.QUEST_DRAFT)).thenReturn(draft);
        when(request.getParameter(Key.SAVE_QUESTION)).thenReturn(Key.SAVE_QUESTION);
        when(request.getParameter(Key.QUESTION_ID)).thenReturn(String.valueOf(draftQuestion.getId()));
        when(request.getParameter(Key.QUESTION_NAME)).thenReturn(draftQuestion.getName());
        when(request.getParameter(Key.QUESTION_TEXT)).thenReturn(draftQuestion.getText());
        when(request.getParameter(Key.QUESTION_GAME_STATE)).thenReturn(draftQuestion.getGameState().name());
        doNothing().when(response).sendRedirect(Go.CREATE_QUEST);

        createQuest.doPost(request, response);

        verify(request).getSession();
        verify(session).getAttribute(eq(Key.QUEST_DRAFT));
        verify(request).getParameter(eq(Key.SAVE_QUESTION));
        verify(request).getParameter(eq(Key.QUESTION_NAME));
        verify(request).getParameter(eq(Key.QUESTION_TEXT));
        verify(request).getParameter(eq(Key.QUESTION_GAME_STATE));
        verify(response).sendRedirect(Go.CREATE_QUEST);
    }

    @Test
    void thenUserCreateNewQuestion_thenQuestionCreated() throws ServletException, IOException {
        Question draftQuestion = Question.builder()
                .name("Question")
                .gameState(GameState.PLAY)
                .build();
        Quest draft = Quest.builder()
                .isDraft(true)
                .questions(new ArrayList<>())
                .build();
        Components.getComponent(QuestionService.class).createQuestion(draftQuestion);
        Components.getComponent(QuestService.class).createQuest(draft);
        draft.getQuestions().add(draftQuestion);
        assertNotNull(draftQuestion.getId());
        assertNotNull(draft.getId());

        when(session.getAttribute(Key.QUEST_DRAFT)).thenReturn(draft);
        when(request.getParameter(Key.SAVE_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.ADD_QUESTION)).thenReturn(Key.ADD_QUESTION);
        doNothing().when(response).sendRedirect(Go.CREATE_QUEST);

        createQuest.doPost(request, response);

        verify(request).getSession();
        verify(session).getAttribute(eq(Key.QUEST_DRAFT));
        verify(request).getParameter(eq(Key.SAVE_QUESTION));
        verify(request).getParameter(eq(Key.ADD_QUESTION));
        verify(response).sendRedirect(Go.CREATE_QUEST);
    }

    @Test
    void whenUserDeleteQuestion_thenQuestionDeleted() throws IOException, ServletException {
        Question draftQuestion = Question.builder()
                .name("Question")
                .gameState(GameState.PLAY)
                .build();
        Quest draft = Quest.builder()
                .isDraft(true)
                .questions(new ArrayList<>())
                .build();
        Components.getComponent(QuestionService.class).createQuestion(draftQuestion);
        Components.getComponent(QuestService.class).createQuest(draft);
        draft.getQuestions().add(draftQuestion);
        assertNotNull(draftQuestion.getId());
        assertNotNull(draft.getId());

        when(session.getAttribute(Key.QUEST_DRAFT)).thenReturn(draft);
        when(request.getParameter(Key.SAVE_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.ADD_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.DELETE_QUESTION)).thenReturn(Key.DELETE_QUESTION);
        when(request.getParameter(Key.QUESTION_ID)).thenReturn(String.valueOf(draftQuestion.getId()));
        doNothing().when(response).sendRedirect(Go.CREATE_QUEST);

        createQuest.doPost(request, response);

        verify(request).getSession();
        verify(session).getAttribute(eq(Key.QUEST_DRAFT));
        verify(request).getParameter(eq(Key.SAVE_QUESTION));
        verify(request).getParameter(eq(Key.ADD_QUESTION));
        verify(request).getParameter(eq(Key.DELETE_QUESTION));
        verify(request).getParameter(eq(Key.QUESTION_ID));
        verify(response).sendRedirect(Go.CREATE_QUEST);
        assertFalse(draft.getQuestions().contains(draftQuestion));
    }

    @Test
    void whenUserAddAnswer_thenAnswerAdded() throws IOException, ServletException {
        Question draftQuestion = Question.builder()
                .name("Question")
                .gameState(GameState.PLAY)
                .answers(new ArrayList<>())
                .build();
        Quest draft = Quest.builder()
                .isDraft(true)
                .questions(new ArrayList<>())
                .build();
        Components.getComponent(QuestionService.class).createQuestion(draftQuestion);
        Components.getComponent(QuestService.class).createQuest(draft);
        draft.getQuestions().add(draftQuestion);
        assertNotNull(draftQuestion.getId());
        assertNotNull(draft.getId());

        when(session.getAttribute(Key.QUEST_DRAFT)).thenReturn(draft);
        when(request.getParameter(Key.SAVE_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.ADD_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.DELETE_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.ADD_ANSWER)).thenReturn(Key.ADD_ANSWER);
        when(request.getParameter(Key.QUESTION_ID)).thenReturn(String.valueOf(draftQuestion.getId()));
        doNothing().when(response).sendRedirect(Go.CREATE_QUEST);

        createQuest.doPost(request, response);

        verify(request).getSession();
        verify(session).getAttribute(eq(Key.QUEST_DRAFT));
        verify(request).getParameter(eq(Key.SAVE_QUESTION));
        verify(request).getParameter(eq(Key.ADD_QUESTION));
        verify(request).getParameter(eq(Key.DELETE_QUESTION));
        verify(request).getParameter(eq(Key.ADD_ANSWER));
        verify(request).getParameter(eq(Key.QUESTION_ID));
        verify(response).sendRedirect(Go.CREATE_QUEST);
        assertFalse(draftQuestion.getAnswers().isEmpty());
    }

    @Test
    void whenUserSaveAnswer_thenAnswerSavedWithNextQuestionId() throws IOException, ServletException {

        Question draftQuestion = Question.builder()
                .name("Question")
                .gameState(GameState.PLAY)
                .answers(new ArrayList<>())
                .build();
        Quest draft = Quest.builder()
                .isDraft(true)
                .questions(new ArrayList<>())
                .build();
        Components.getComponent(QuestionService.class).createQuestion(draftQuestion);
        Components.getComponent(QuestService.class).createQuest(draft);
        Answer draftAnswer = Answer.builder()
                .questionId(draftQuestion.getId())
                .build();
        Components.getComponent(AnswerService.class).createAnswer(draftAnswer);
        draft.getQuestions().add(draftQuestion);
        draftQuestion.getAnswers().add(draftAnswer);
        assertNotNull(draftQuestion.getId());
        assertNotNull(draftAnswer.getId());
        assertNotNull(draft.getId());

        when(session.getAttribute(Key.QUEST_DRAFT)).thenReturn(draft);
        when(request.getParameter(Key.SAVE_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.ADD_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.DELETE_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.ADD_ANSWER)).thenReturn(null);
        when(request.getParameter(Key.SAVE_ANSWER)).thenReturn(Key.SAVE_ANSWER);
        when(request.getParameter(Key.ANSWER_ID)).thenReturn(String.valueOf(draftAnswer.getId()));
        when(request.getParameter(Key.ANSWER_TEXT)).thenReturn("Answer Test Text");
        when(request.getParameter(Key.ANSWER_NEXT_QUESTION_ID)).thenReturn(String.valueOf(2L));
        doNothing().when(response).sendRedirect(Go.CREATE_QUEST);

        createQuest.doPost(request, response);

        verify(request).getSession();
        verify(session).getAttribute(eq(Key.QUEST_DRAFT));
        verify(request).getParameter(eq(Key.SAVE_QUESTION));
        verify(request).getParameter(eq(Key.ADD_QUESTION));
        verify(request).getParameter(eq(Key.DELETE_QUESTION));
        verify(request).getParameter(eq(Key.ADD_ANSWER));
        verify(request).getParameter(eq(Key.SAVE_ANSWER));
        verify(request).getParameter(eq(Key.ANSWER_ID));
        verify(request).getParameter(eq(Key.ANSWER_TEXT));
        verify(request, times(2)).getParameter(eq(Key.ANSWER_NEXT_QUESTION_ID));
        verify(response).sendRedirect(Go.CREATE_QUEST);
    }

    @Test
    void whenUserSaveAnswer_thenAnswerSavedWithoutNextQuestionId() throws IOException, ServletException {

        Question draftQuestion = Question.builder()
                .name("Question")
                .gameState(GameState.PLAY)
                .answers(new ArrayList<>())
                .build();
        Quest draft = Quest.builder()
                .isDraft(true)
                .questions(new ArrayList<>())
                .build();
        Components.getComponent(QuestionService.class).createQuestion(draftQuestion);
        Components.getComponent(QuestService.class).createQuest(draft);
        Answer draftAnswer = Answer.builder()
                .questionId(draftQuestion.getId())
                .build();
        Components.getComponent(AnswerService.class).createAnswer(draftAnswer);
        draft.getQuestions().add(draftQuestion);
        draftQuestion.getAnswers().add(draftAnswer);
        assertNotNull(draftQuestion.getId());
        assertNotNull(draftAnswer.getId());
        assertNotNull(draft.getId());

        when(session.getAttribute(Key.QUEST_DRAFT)).thenReturn(draft);
        when(request.getParameter(Key.SAVE_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.ADD_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.DELETE_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.ADD_ANSWER)).thenReturn(null);
        when(request.getParameter(Key.SAVE_ANSWER)).thenReturn(Key.SAVE_ANSWER);
        when(request.getParameter(Key.ANSWER_ID)).thenReturn(String.valueOf(draftAnswer.getId()));
        when(request.getParameter(Key.ANSWER_TEXT)).thenReturn("Answer Test Text");
        when(request.getParameter(Key.ANSWER_NEXT_QUESTION_ID)).thenReturn(null);
        doNothing().when(response).sendRedirect(Go.CREATE_QUEST);

        createQuest.doPost(request, response);

        verify(request).getSession();
        verify(session).getAttribute(eq(Key.QUEST_DRAFT));
        verify(request).getParameter(eq(Key.SAVE_QUESTION));
        verify(request).getParameter(eq(Key.ADD_QUESTION));
        verify(request).getParameter(eq(Key.DELETE_QUESTION));
        verify(request).getParameter(eq(Key.ADD_ANSWER));
        verify(request).getParameter(eq(Key.SAVE_ANSWER));
        verify(request).getParameter(eq(Key.ANSWER_ID));
        verify(request).getParameter(eq(Key.ANSWER_TEXT));
        verify(request).getParameter(eq(Key.ANSWER_NEXT_QUESTION_ID));
        verify(response).sendRedirect(Go.CREATE_QUEST);
    }

    @Test
    void whenDeleteAnswer_thenAnswerDeleted() throws IOException, ServletException {

        Question draftQuestion = Question.builder()
                .name("Question")
                .gameState(GameState.PLAY)
                .answers(new ArrayList<>())
                .build();
        Quest draft = Quest.builder()
                .isDraft(true)
                .questions(new ArrayList<>())
                .build();
        Components.getComponent(QuestionService.class).createQuestion(draftQuestion);
        Components.getComponent(QuestService.class).createQuest(draft);
        Answer draftAnswer = Answer.builder()
                .questionId(draftQuestion.getId())
                .build();
        Components.getComponent(AnswerService.class).createAnswer(draftAnswer);
        draft.getQuestions().add(draftQuestion);
        draftQuestion.getAnswers().add(draftAnswer);
        assertNotNull(draftQuestion.getId());
        assertNotNull(draftAnswer.getId());
        assertNotNull(draft.getId());

        when(session.getAttribute(Key.QUEST_DRAFT)).thenReturn(draft);
        when(request.getParameter(Key.SAVE_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.ADD_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.DELETE_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.ADD_ANSWER)).thenReturn(null);
        when(request.getParameter(Key.SAVE_ANSWER)).thenReturn(null);
        when(request.getParameter(Key.DELETE_ANSWER)).thenReturn(Key.DELETE_ANSWER);
        when(request.getParameter(Key.ANSWER_ID)).thenReturn(String.valueOf(draftAnswer.getId()));
        when(request.getParameter(Key.QUESTION_ID)).thenReturn(String.valueOf(draftQuestion.getId()));
        doNothing().when(response).sendRedirect(Go.CREATE_QUEST);

        createQuest.doPost(request, response);

        verify(request).getSession();
        verify(session).getAttribute(eq(Key.QUEST_DRAFT));
        verify(request).getParameter(eq(Key.SAVE_QUESTION));
        verify(request).getParameter(eq(Key.ADD_QUESTION));
        verify(request).getParameter(eq(Key.DELETE_QUESTION));
        verify(request).getParameter(eq(Key.ADD_ANSWER));
        verify(request).getParameter(eq(Key.SAVE_ANSWER));
        verify(request).getParameter(eq(Key.DELETE_ANSWER));
        verify(request).getParameter(eq(Key.ANSWER_ID));
        verify(request).getParameter(eq(Key.QUESTION_ID));
        verify(response).sendRedirect(Go.CREATE_QUEST);
        assertFalse(draftQuestion.getAnswers().contains(draftAnswer));
    }

    @Test
    void whenUserSaveQuestParams_thenParamsAreSavedWithStartQuestionId() throws IOException, ServletException {
        Quest draft = Quest.builder()
                .isDraft(true)
                .questions(new ArrayList<>())
                .build();
        Components.getComponent(QuestService.class).createQuest(draft);
        assertNotNull(draft.getId());

        when(session.getAttribute(Key.QUEST_DRAFT)).thenReturn(draft);
        when(request.getParameter(Key.SAVE_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.ADD_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.DELETE_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.ADD_ANSWER)).thenReturn(null);
        when(request.getParameter(Key.SAVE_ANSWER)).thenReturn(null);
        when(request.getParameter(Key.DELETE_ANSWER)).thenReturn(null);
        when(request.getParameter(Key.SAVE_QUEST_PARAMS)).thenReturn(Key.SAVE_QUEST_PARAMS);
        when(request.getParameter(Key.QUEST_NAME)).thenReturn("Test Name");
        when(request.getParameter(Key.QUEST_START_QUESTION_ID)).thenReturn(String.valueOf(1L));
        doNothing().when(response).sendRedirect(Go.CREATE_QUEST);

        createQuest.doPost(request, response);

        verify(request).getSession();
        verify(session).getAttribute(eq(Key.QUEST_DRAFT));
        verify(request).getParameter(eq(Key.SAVE_QUESTION));
        verify(request).getParameter(eq(Key.ADD_QUESTION));
        verify(request).getParameter(eq(Key.DELETE_QUESTION));
        verify(request).getParameter(eq(Key.ADD_ANSWER));
        verify(request).getParameter(eq(Key.SAVE_ANSWER));
        verify(request).getParameter(eq(Key.DELETE_ANSWER));
        verify(request).getParameter(eq(Key.SAVE_QUEST_PARAMS));
        verify(request).getParameter(eq(Key.QUEST_NAME));
        verify(request, times(2)).getParameter(eq(Key.QUEST_START_QUESTION_ID));
        verify(response).sendRedirect(Go.CREATE_QUEST);

        assertEquals("Test Name", draft.getName());
        assertEquals(1L, draft.getStartQuestionId());

    }

    @Test
    void whenUserSaveQuestParams_thenParamsAreSavedWithoutStartQuestionId() throws IOException, ServletException {
        Quest draft = Quest.builder()
                .isDraft(true)
                .questions(new ArrayList<>())
                .build();
        Components.getComponent(QuestService.class).createQuest(draft);
        assertNotNull(draft.getId());

        when(session.getAttribute(Key.QUEST_DRAFT)).thenReturn(draft);
        when(request.getParameter(Key.SAVE_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.ADD_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.DELETE_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.ADD_ANSWER)).thenReturn(null);
        when(request.getParameter(Key.SAVE_ANSWER)).thenReturn(null);
        when(request.getParameter(Key.DELETE_ANSWER)).thenReturn(null);
        when(request.getParameter(Key.SAVE_QUEST_PARAMS)).thenReturn(Key.SAVE_QUEST_PARAMS);
        when(request.getParameter(Key.QUEST_NAME)).thenReturn("Test Name");
        when(request.getParameter(Key.QUEST_START_QUESTION_ID)).thenReturn(null);
        doNothing().when(response).sendRedirect(Go.CREATE_QUEST);

        createQuest.doPost(request, response);

        verify(request).getSession();
        verify(session).getAttribute(eq(Key.QUEST_DRAFT));
        verify(request).getParameter(eq(Key.SAVE_QUESTION));
        verify(request).getParameter(eq(Key.ADD_QUESTION));
        verify(request).getParameter(eq(Key.DELETE_QUESTION));
        verify(request).getParameter(eq(Key.ADD_ANSWER));
        verify(request).getParameter(eq(Key.SAVE_ANSWER));
        verify(request).getParameter(eq(Key.DELETE_ANSWER));
        verify(request).getParameter(eq(Key.SAVE_QUEST_PARAMS));
        verify(request).getParameter(eq(Key.QUEST_NAME));
        verify(request).getParameter(eq(Key.QUEST_START_QUESTION_ID));
        verify(response).sendRedirect(Go.CREATE_QUEST);

        assertEquals("Test Name", draft.getName());
    }

    @Test
    void whenUserPublishQuest_thenQuestArePublished() throws IOException, ServletException {
        Quest draft = Quest.builder()
                .isDraft(true)
                .questions(new ArrayList<>())
                .build();
        Components.getComponent(QuestService.class).createQuest(draft);
        assertNotNull(draft.getId());

        when(session.getAttribute(Key.QUEST_DRAFT)).thenReturn(draft);
        when(request.getParameter(Key.SAVE_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.ADD_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.DELETE_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.ADD_ANSWER)).thenReturn(null);
        when(request.getParameter(Key.SAVE_ANSWER)).thenReturn(null);
        when(request.getParameter(Key.DELETE_ANSWER)).thenReturn(null);
        when(request.getParameter(Key.SAVE_QUEST_PARAMS)).thenReturn(null);
        when(request.getParameter(Key.PUBLISH_QUEST)).thenReturn(Key.PUBLISH_QUEST);
        doNothing().when(session).removeAttribute(Key.QUEST_DRAFT);
        doNothing().when(response).sendRedirect(Key.FORMAT_LINK_ID.formatted(Go.PLAY, draft.getId()));

        createQuest.doPost(request, response);

        verify(request).getSession();
        verify(session).getAttribute(eq(Key.QUEST_DRAFT));
        verify(request).getParameter(eq(Key.SAVE_QUESTION));
        verify(request).getParameter(eq(Key.ADD_QUESTION));
        verify(request).getParameter(eq(Key.DELETE_QUESTION));
        verify(request).getParameter(eq(Key.ADD_ANSWER));
        verify(request).getParameter(eq(Key.SAVE_ANSWER));
        verify(request).getParameter(eq(Key.DELETE_ANSWER));
        verify(request).getParameter(eq(Key.SAVE_QUEST_PARAMS));
        verify(request).getParameter(eq(Key.PUBLISH_QUEST));
        verify(session).removeAttribute(eq(Key.QUEST_DRAFT));
        verify(response).sendRedirect(eq(Key.FORMAT_LINK_ID.formatted(Go.PLAY, draft.getId())));
        assertFalse(draft.getIsDraft());
    }

    @Test
    void whenUserPostWithoutParams_thenRedirectCreateQuest() throws IOException, ServletException {
        Quest draft = Quest.builder()
                .isDraft(true)
                .questions(new ArrayList<>())
                .build();
        Components.getComponent(QuestService.class).createQuest(draft);
        assertNotNull(draft.getId());

        when(session.getAttribute(Key.QUEST_DRAFT)).thenReturn(draft);
        when(request.getParameter(Key.SAVE_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.ADD_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.DELETE_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.ADD_ANSWER)).thenReturn(null);
        when(request.getParameter(Key.SAVE_ANSWER)).thenReturn(null);
        when(request.getParameter(Key.DELETE_ANSWER)).thenReturn(null);
        when(request.getParameter(Key.SAVE_QUEST_PARAMS)).thenReturn(null);
        when(request.getParameter(Key.PUBLISH_QUEST)).thenReturn(null);
        doNothing().when(session).removeAttribute(Key.QUEST_DRAFT);
        doNothing().when(response).sendRedirect(Key.FORMAT_LINK_ID.formatted(Go.PLAY, draft.getId()));

        createQuest.doPost(request, response);

        verify(request).getSession();
        verify(session).getAttribute(eq(Key.QUEST_DRAFT));
        verify(request).getParameter(eq(Key.SAVE_QUESTION));
        verify(request).getParameter(eq(Key.ADD_QUESTION));
        verify(request).getParameter(eq(Key.DELETE_QUESTION));
        verify(request).getParameter(eq(Key.ADD_ANSWER));
        verify(request).getParameter(eq(Key.SAVE_ANSWER));
        verify(request).getParameter(eq(Key.DELETE_ANSWER));
        verify(request).getParameter(eq(Key.SAVE_QUEST_PARAMS));
        verify(request).getParameter(eq(Key.PUBLISH_QUEST));
        verify(response).sendRedirect(eq(Go.CREATE_QUEST));
    }
}
