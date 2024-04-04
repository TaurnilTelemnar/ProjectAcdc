package com.javarush.kojin.cmd;

import com.javarush.kojin.BaseIT;
import com.javarush.kojin.config.Components;
import com.javarush.kojin.entity.Quest;
import com.javarush.kojin.entity.Role;
import com.javarush.kojin.entity.User;
import com.javarush.kojin.repository.QuestRepository;
import com.javarush.kojin.repository.UserRepository;
import com.javarush.kojin.util.Go;
import com.javarush.kojin.util.Key;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

class HomeIT extends BaseIT {
    private final Home home = (Home) Components.getCommand(Go.HOME);

    @Test
    void whenUserGoHome_thenForwardToHomeJsp() throws ServletException, IOException {
        User user = Components.getComponent(UserRepository.class).get(2L);
        when(session.getAttribute(Key.USER)).thenReturn(user);
        when(request.getRequestDispatcher(Go.HOME_JSP)).thenReturn(requestDispatcher);

        home.doGet(request, response);

        verify(request).getSession();
        verify(session).getAttribute(eq(Key.USER));
        verify(request).getRequestDispatcher(eq(Go.HOME_JSP));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void whenGuestGoHome_thenForwardToHomeJsp() throws ServletException, IOException {
        User user = User.builder()
                .role(Role.GUEST)
                .userQuests(new ArrayList<>())
                .build();

        user.getUserQuests().add(Quest.builder().isDraft(true).authorId(user.getId()).build());
        when(session.getAttribute(Key.USER)).thenReturn(user);
        when(request.getRequestDispatcher(Go.HOME_JSP)).thenReturn(requestDispatcher);

        home.doGet(request, response);

        verify(request).getSession();
        verify(session).getAttribute(eq(Key.USER));
        verify(request).getRequestDispatcher(eq(Go.HOME_JSP));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void whenUserPostHome_thenRedirectHome() throws ServletException, IOException {
        home.doPost(request, response);

        verify(response).sendRedirect(eq(Go.HOME));
    }

    @Test
    void whenGuestGoHomeWithoutDraft_thenForwardToHomeJsp() throws ServletException, IOException {
        Quest draft = Quest.builder()
                .isDraft(true)
                .authorId(1L)
                .build();
        Components.getComponent(QuestRepository.class).create(draft);

        User user = User.builder()
                .role(Role.GUEST)
                .build();

        when(session.getAttribute(Key.USER)).thenReturn(user);
        when(request.getRequestDispatcher(Go.HOME_JSP)).thenReturn(requestDispatcher);

        home.doGet(request, response);

        verify(request).getSession();
        verify(session).getAttribute(eq(Key.USER));
        verify(request).getRequestDispatcher(eq(Go.HOME_JSP));
        verify(requestDispatcher).forward(request, response);
    }
}