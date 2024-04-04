package com.javarush.kojin.cmd;

import com.javarush.kojin.BaseIT;
import com.javarush.kojin.config.Components;
import com.javarush.kojin.entity.GameState;
import com.javarush.kojin.entity.Quest;
import com.javarush.kojin.entity.Question;
import com.javarush.kojin.repository.QuestionRepository;
import com.javarush.kojin.util.Go;
import com.javarush.kojin.util.Key;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EditQuestIT extends BaseIT {
    private final EditQuest editQuest = (EditQuest) Components.getCommand(Go.EDIT_QUEST);

    @Test
    void whenGetQuestFromRepo_thenRedirectError() throws ServletException, IOException {
        when(request.getParameter(Key.ID)).thenReturn(null);

        editQuest.doGet(request, response);

        verify(request).getParameter(eq(Key.ID));
        verify(request).getSession();
        verify(session).setAttribute(eq(Key.ERROR), eq(Key.ERROR_404));
        verify(response).sendRedirect(eq(Go.ERROR));
    }

    @Test
    void whenGetQuestFromRepo_thenForwardEditQuestJsp() throws ServletException, IOException {
        when(request.getParameter(Key.ID)).thenReturn("1");
        when(request.getRequestDispatcher(Key.FORMAT_LINK_ID.formatted(Go.EDIT_QUEST_JSP, 1))).thenReturn(requestDispatcher);
        editQuest.doGet(request, response);

        verify(request, times(2)).getParameter(eq(Key.ID));
        verify(request).setAttribute(eq(Key.QUEST), any(Quest.class));
        verify(request).getRequestDispatcher(eq(Key.FORMAT_LINK_ID.formatted(Go.EDIT_QUEST_JSP, 1)));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void whenPostQuestFromRepo_thenRedirectError() throws ServletException, IOException {
        when(request.getParameter(Key.ID)).thenReturn(null);

        editQuest.doPost(request, response);

        verify(request).getParameter(eq(Key.ID));
        verify(request).getSession();
        verify(session).setAttribute(eq(Key.ERROR), eq(Key.ERROR_404));
        verify(response).sendRedirect(eq(Go.ERROR));
    }

    @Test
    void whenUserSaveQuestion_thenQuestionSaved() throws IOException, ServletException {

        when(request.getParameter(Key.SAVE_QUESTION)).thenReturn(Key.SAVE_QUESTION);
        when(request.getParameter(Key.QUESTION_ID)).thenReturn("1");
        when(request.getParameter(Key.ID)).thenReturn("1");
        String newQuestionName = "New Question Name";
        when(request.getParameter(Key.QUESTION_NAME)).thenReturn(newQuestionName);
        String newQuestionText = "New Question Text";
        when(request.getParameter(Key.QUESTION_TEXT)).thenReturn(newQuestionText);
        GameState questionGameState = GameState.LOSE;
        when(request.getParameter(Key.QUESTION_GAME_STATE)).thenReturn(questionGameState.name());

        editQuest.doPost(request, response);

        verify(request, times(2)).getParameter(eq(Key.ID));
        verify(request).getParameter(eq(Key.SAVE_QUESTION));
        verify(request).getParameter(eq(Key.QUESTION_NAME));
        verify(request).getParameter(eq(Key.QUESTION_TEXT));
        verify(request).getParameter(eq(Key.QUESTION_GAME_STATE));
        verify(response).sendRedirect(eq(Key.FORMAT_LINK_ID.formatted(Go.EDIT_QUEST, 1)));

        Question testQuestion = Components.getComponent(QuestionRepository.class).get(1L);

        assertEquals(newQuestionName, testQuestion.getName());
        assertEquals(newQuestionText, testQuestion.getText());
        assertEquals(questionGameState, testQuestion.getGameState());
    }

    @Test
    void whenUserAddQuestion_thenQuestionAdded() throws ServletException, IOException {
        when(request.getParameter(Key.SAVE_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.ADD_QUESTION)).thenReturn(Key.ADD_QUESTION);
        when(request.getParameter(Key.ID)).thenReturn("1");

        editQuest.doPost(request, response);

        verify(request, times(2)).getParameter(eq(Key.ID));
        verify(request).getParameter(eq(Key.SAVE_QUESTION));
        verify(request).getParameter(eq(Key.ADD_QUESTION));
        verify(response).sendRedirect(eq(Key.FORMAT_LINK_ID.formatted(Go.EDIT_QUEST, 1)));
    }

    @Test
    void whenUserDeleteQuestion_thenQuestionDeleted() throws ServletException, IOException {
        when(request.getParameter(Key.SAVE_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.ADD_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.DELETE_QUESTION)).thenReturn(Key.DELETE_QUESTION);
        when(request.getParameter(Key.ID)).thenReturn("1");
        when(request.getParameter(Key.QUESTION_ID)).thenReturn("1");

        editQuest.doPost(request, response);

        verify(request, times(2)).getParameter(eq(Key.ID));
        verify(request).getParameter(eq(Key.SAVE_QUESTION));
        verify(request).getParameter(eq(Key.ADD_QUESTION));
        verify(request).getParameter(eq(Key.DELETE_QUESTION));
        verify(request).getParameter(eq(Key.QUESTION_ID));
        verify(response).sendRedirect(eq(Key.FORMAT_LINK_ID.formatted(Go.EDIT_QUEST, 1)));
    }

    @Test
    void whenUserAddAnswer_thenAnswerAdded() throws ServletException, IOException {
        when(request.getParameter(Key.SAVE_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.ADD_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.DELETE_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.ADD_ANSWER)).thenReturn(Key.ADD_ANSWER);
        when(request.getParameter(Key.ID)).thenReturn("1");
        when(request.getParameter(Key.QUESTION_ID)).thenReturn("1");

        editQuest.doPost(request, response);

        verify(request, times(2)).getParameter(eq(Key.ID));
        verify(request).getParameter(eq(Key.SAVE_QUESTION));
        verify(request).getParameter(eq(Key.ADD_QUESTION));
        verify(request).getParameter(eq(Key.DELETE_QUESTION));
        verify(request).getParameter(eq(Key.ADD_ANSWER));
        verify(request).getParameter(eq(Key.QUESTION_ID));
        verify(response).sendRedirect(eq(Key.FORMAT_LINK_ID.formatted(Go.EDIT_QUEST, 1)));
    }

    @Test
    void whenUserSaveAnswerWithoutNextQuestionId_thenAnswerSaved() throws ServletException, IOException {
        when(request.getParameter(Key.SAVE_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.ADD_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.DELETE_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.ADD_ANSWER)).thenReturn(null);
        when(request.getParameter(Key.SAVE_ANSWER)).thenReturn(Key.SAVE_ANSWER);
        when(request.getParameter(Key.ID)).thenReturn("1");
        when(request.getParameter(Key.ANSWER_ID)).thenReturn("1");
        when(request.getParameter(Key.ANSWER_NEXT_QUESTION_ID)).thenReturn(null);
        when(request.getParameter(Key.ANSWER_TEXT)).thenReturn("Some Answer Text");

        editQuest.doPost(request, response);

        verify(request, times(2)).getParameter(eq(Key.ID));
        verify(request).getParameter(eq(Key.SAVE_QUESTION));
        verify(request).getParameter(eq(Key.ADD_QUESTION));
        verify(request).getParameter(eq(Key.DELETE_QUESTION));
        verify(request).getParameter(eq(Key.ADD_ANSWER));
        verify(request).getParameter(eq(Key.SAVE_ANSWER));
        verify(request).getParameter(eq(Key.ANSWER_ID));
        verify(request).getParameter(eq(Key.ANSWER_TEXT));
        verify(request).getParameter(eq(Key.ANSWER_NEXT_QUESTION_ID));
        verify(response).sendRedirect(eq(Key.FORMAT_LINK_ID.formatted(Go.EDIT_QUEST, 1)));
    }

    @Test
    void whenUserSaveAnswerWithNextQuestionId_thenAnswerSaved() throws ServletException, IOException {
        when(request.getParameter(Key.SAVE_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.ADD_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.DELETE_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.ADD_ANSWER)).thenReturn(null);
        when(request.getParameter(Key.SAVE_ANSWER)).thenReturn(Key.SAVE_ANSWER);
        when(request.getParameter(Key.ID)).thenReturn("1");
        when(request.getParameter(Key.ANSWER_ID)).thenReturn("1");
        when(request.getParameter(Key.ANSWER_NEXT_QUESTION_ID)).thenReturn("1");
        when(request.getParameter(Key.ANSWER_TEXT)).thenReturn("Some Answer New Text");

        editQuest.doPost(request, response);

        verify(request, times(2)).getParameter(eq(Key.ID));
        verify(request).getParameter(eq(Key.SAVE_QUESTION));
        verify(request).getParameter(eq(Key.ADD_QUESTION));
        verify(request).getParameter(eq(Key.DELETE_QUESTION));
        verify(request).getParameter(eq(Key.ADD_ANSWER));
        verify(request).getParameter(eq(Key.SAVE_ANSWER));
        verify(request).getParameter(eq(Key.ANSWER_ID));
        verify(request).getParameter(eq(Key.ANSWER_TEXT));
        verify(request, times(2)).getParameter(eq(Key.ANSWER_NEXT_QUESTION_ID));
        verify(response).sendRedirect(eq(Key.FORMAT_LINK_ID.formatted(Go.EDIT_QUEST, 1)));
    }

    @Test
    void whenUserDeleteAnswer_thenAnswerDeleted() throws ServletException, IOException {
        when(request.getParameter(Key.SAVE_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.ADD_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.DELETE_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.ADD_ANSWER)).thenReturn(null);
        when(request.getParameter(Key.SAVE_ANSWER)).thenReturn(null);
        when(request.getParameter(Key.DELETE_ANSWER)).thenReturn(Key.DELETE_ANSWER);
        when(request.getParameter(Key.ID)).thenReturn("1");
        when(request.getParameter(Key.ANSWER_ID)).thenReturn("1");
        when(request.getParameter(Key.QUESTION_ID)).thenReturn("1");

        editQuest.doPost(request, response);

        verify(request, times(2)).getParameter(eq(Key.ID));
        verify(request).getParameter(eq(Key.SAVE_QUESTION));
        verify(request).getParameter(eq(Key.ADD_QUESTION));
        verify(request).getParameter(eq(Key.DELETE_QUESTION));
        verify(request).getParameter(eq(Key.ADD_ANSWER));
        verify(request).getParameter(eq(Key.SAVE_ANSWER));
        verify(request).getParameter(eq(Key.DELETE_ANSWER));
        verify(request).getParameter(eq(Key.ANSWER_ID));
        verify(request).getParameter(eq(Key.QUESTION_ID));
        verify(response).sendRedirect(eq(Key.FORMAT_LINK_ID.formatted(Go.EDIT_QUEST, 1)));
    }

    @Test
    void whenUserSaveQuestWithStartQuestionId_thenQuestSaved() throws ServletException, IOException {
        when(request.getParameter(Key.SAVE_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.ADD_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.DELETE_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.ADD_ANSWER)).thenReturn(null);
        when(request.getParameter(Key.SAVE_ANSWER)).thenReturn(null);
        when(request.getParameter(Key.DELETE_ANSWER)).thenReturn(null);
        when(request.getParameter(Key.SAVE_QUEST_PARAMS)).thenReturn(Key.SAVE_QUEST_PARAMS);
        when(request.getParameter(Key.ID)).thenReturn("1");
        when(request.getParameter(Key.QUEST_NAME)).thenReturn("Some Quest Name");
        when(request.getParameter(Key.QUEST_START_QUESTION_ID)).thenReturn("1");


        editQuest.doPost(request, response);

        verify(request, times(2)).getParameter(eq(Key.ID));
        verify(request).getParameter(eq(Key.SAVE_QUESTION));
        verify(request).getParameter(eq(Key.ADD_QUESTION));
        verify(request).getParameter(eq(Key.DELETE_QUESTION));
        verify(request).getParameter(eq(Key.ADD_ANSWER));
        verify(request).getParameter(eq(Key.SAVE_ANSWER));
        verify(request).getParameter(eq(Key.DELETE_ANSWER));
        verify(request).getParameter(eq(Key.SAVE_QUEST_PARAMS));
        verify(request).getParameter(eq(Key.QUEST_NAME));
        verify(request, times(2)).getParameter(eq(Key.QUEST_START_QUESTION_ID));
        verify(response).sendRedirect(eq(Key.FORMAT_LINK_ID.formatted(Go.EDIT_QUEST, 1)));
    }

    @Test
    void whenUserSaveQuestWithoutStartQuestionId_thenQuestSaved() throws ServletException, IOException {
        when(request.getParameter(Key.SAVE_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.ADD_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.DELETE_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.ADD_ANSWER)).thenReturn(null);
        when(request.getParameter(Key.SAVE_ANSWER)).thenReturn(null);
        when(request.getParameter(Key.DELETE_ANSWER)).thenReturn(null);
        when(request.getParameter(Key.SAVE_QUEST_PARAMS)).thenReturn(Key.SAVE_QUEST_PARAMS);
        when(request.getParameter(Key.ID)).thenReturn("1");
        when(request.getParameter(Key.QUEST_NAME)).thenReturn("Some New Quest Name");
        when(request.getParameter(Key.QUEST_START_QUESTION_ID)).thenReturn(null);


        editQuest.doPost(request, response);

        verify(request, times(2)).getParameter(eq(Key.ID));
        verify(request).getParameter(eq(Key.SAVE_QUESTION));
        verify(request).getParameter(eq(Key.ADD_QUESTION));
        verify(request).getParameter(eq(Key.DELETE_QUESTION));
        verify(request).getParameter(eq(Key.ADD_ANSWER));
        verify(request).getParameter(eq(Key.SAVE_ANSWER));
        verify(request).getParameter(eq(Key.DELETE_ANSWER));
        verify(request).getParameter(eq(Key.SAVE_QUEST_PARAMS));
        verify(request).getParameter(eq(Key.QUEST_NAME));
        verify(request).getParameter(eq(Key.QUEST_START_QUESTION_ID));
        verify(response).sendRedirect(eq(Key.FORMAT_LINK_ID.formatted(Go.EDIT_QUEST, 1)));
    }

    @Test
    void whenUserSaveQuestWithoutAnyParams_thenRedirectEditQuest() throws ServletException, IOException {
        when(request.getParameter(Key.SAVE_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.ADD_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.DELETE_QUESTION)).thenReturn(null);
        when(request.getParameter(Key.ADD_ANSWER)).thenReturn(null);
        when(request.getParameter(Key.SAVE_ANSWER)).thenReturn(null);
        when(request.getParameter(Key.DELETE_ANSWER)).thenReturn(null);
        when(request.getParameter(Key.SAVE_QUEST_PARAMS)).thenReturn(null);
        when(request.getParameter(Key.ID)).thenReturn("1");

        editQuest.doPost(request, response);

        verify(request, times(2)).getParameter(eq(Key.ID));
        verify(request).getParameter(eq(Key.SAVE_QUESTION));
        verify(request).getParameter(eq(Key.ADD_QUESTION));
        verify(request).getParameter(eq(Key.DELETE_QUESTION));
        verify(request).getParameter(eq(Key.ADD_ANSWER));
        verify(request).getParameter(eq(Key.SAVE_ANSWER));
        verify(request).getParameter(eq(Key.DELETE_ANSWER));
        verify(request).getParameter(eq(Key.SAVE_QUEST_PARAMS));
        verify(response).sendRedirect(eq(Key.FORMAT_LINK_ID.formatted(Go.EDIT_QUEST, 1)));
    }
}