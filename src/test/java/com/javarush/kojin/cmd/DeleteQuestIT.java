package com.javarush.kojin.cmd;

import com.javarush.kojin.BaseIT;
import com.javarush.kojin.config.Components;
import com.javarush.kojin.entity.Quest;
import com.javarush.kojin.repository.QuestRepository;
import com.javarush.kojin.repository.UserRepository;
import com.javarush.kojin.util.Go;
import com.javarush.kojin.util.Key;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class DeleteQuestIT extends BaseIT {
    private final DeleteQuest deleteQuest = (DeleteQuest) Components.getCommand(Go.DELETE_QUEST);

    @Test
    void whenInGetGetQuestFromRepo_thenQuestNotExist() throws IOException, ServletException {
        when(request.getParameter(Key.ID)).thenReturn(null);
        doNothing().when(session).setAttribute(Key.ERROR, Key.ERROR_404);
        doNothing().when(response).sendRedirect(Go.ERROR);

        deleteQuest.doGet(request, response);

        verify(request).getParameter(eq(Key.ID));
        verify(request).getSession();
        verify(session).setAttribute(eq(Key.ERROR), eq(Key.ERROR_404));
        verify(response).sendRedirect(eq(Go.ERROR));
    }

    @Test
    void whenGetQuestFromRepo_thenForwardDeleteQuestJsp() throws ServletException, IOException {
        when(request.getParameter(Key.ID)).thenReturn("1");
        doNothing().when(request).setAttribute(Key.QUEST, Quest.class);
        when(request.getRequestDispatcher(Key.FORMAT_LINK_ID.formatted(Go.DELETE_QUEST_JSP, 1))).thenReturn(requestDispatcher);
        doNothing().when(requestDispatcher).forward(request, response);

        deleteQuest.doGet(request, response);

        verify(request, times(2)).getParameter(eq(Key.ID));
        verify(request).setAttribute(eq(Key.QUEST), any(Quest.class));
        verify(request).getRequestDispatcher(eq(Key.FORMAT_LINK_ID.formatted(Go.DELETE_QUEST_JSP, 1)));
        verify(requestDispatcher).forward(request, response);

    }
    @Test
    void whenInPostGetQuestFromRepo_thenQuestNotExist() throws IOException, ServletException {
        when(request.getParameter(Key.ID)).thenReturn(null);
        doNothing().when(session).setAttribute(Key.ERROR, Key.ERROR_404);
        doNothing().when(response).sendRedirect(Go.ERROR);

        deleteQuest.doPost(request, response);

        verify(request).getParameter(eq(Key.ID));
        verify(request).getSession();
        verify(session).setAttribute(eq(Key.ERROR), eq(Key.ERROR_404));
        verify(response).sendRedirect(eq(Go.ERROR));
    }

    @Test
    void whenDeleteQuest_thenQuestDeleted() throws IOException, ServletException {
        Quest quest = Components.getComponent(QuestRepository.class).get(1L);
        assertNotNull(quest);
        when(request.getParameter(Key.ID)).thenReturn("1");
        when(request.getParameter(Go.DELETE_QUEST)).thenReturn(Go.DELETE_QUEST);
        doNothing().when(response).sendRedirect(Go.HOME);

        deleteQuest.doPost(request, response);

        verify(request, times(2)).getParameter(eq(Key.ID));
        verify(request).getParameter(eq(Go.DELETE_QUEST));
        verify(response).sendRedirect(eq(Go.HOME));

        Quest deletedQuest = Components.getComponent(QuestRepository.class).get(1L);
        assertNull(deletedQuest);
    }

    @Test
    void whenDeleteQuestWithoutParam_thenRedirectHome() throws IOException, ServletException {
        Quest quest = Components.getComponent(QuestRepository.class).get(1L);
        assertNotNull(quest);
        when(request.getParameter(Key.ID)).thenReturn("1");
        when(request.getParameter(Go.DELETE_QUEST)).thenReturn(null);
        doNothing().when(response).sendRedirect(Go.HOME);

        deleteQuest.doPost(request, response);

        verify(request, times(2)).getParameter(eq(Key.ID));
        verify(request).getParameter(eq(Go.DELETE_QUEST));
        verify(response).sendRedirect(eq(Go.HOME));
    }

    @Test
    void whenDeleteQuestWithoutAuthor_thenQuestDeleted() throws IOException, ServletException {
        Quest quest = Components.getComponent(QuestRepository.class).get(2L);
        assertNotNull(quest);
        Components.getComponent(UserRepository.class).delete(quest.getAuthorId());
        when(request.getParameter(Key.ID)).thenReturn("2");
        when(request.getParameter(Go.DELETE_QUEST)).thenReturn(Go.DELETE_QUEST);
        doNothing().when(response).sendRedirect(Go.HOME);

        deleteQuest.doPost(request, response);

        verify(request, times(2)).getParameter(eq(Key.ID));
        verify(request).getParameter(eq(Go.DELETE_QUEST));
        verify(response).sendRedirect(eq(Go.HOME));

        Quest deletedQuest = Components.getComponent(QuestRepository.class).get(2L);
        assertNull(deletedQuest);
    }
}