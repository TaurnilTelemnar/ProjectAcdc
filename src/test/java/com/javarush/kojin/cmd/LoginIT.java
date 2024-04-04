package com.javarush.kojin.cmd;

import com.javarush.kojin.BaseIT;
import com.javarush.kojin.config.Components;
import com.javarush.kojin.entity.User;
import com.javarush.kojin.util.Go;
import com.javarush.kojin.util.Key;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;
class LoginIT extends BaseIT {
    private final Login login = (Login) Components.getCommand(Go.LOGIN);

    @Test
    void whenUserGetLogin_thenForwardLoginJsp() throws ServletException, IOException {
        when(request.getRequestDispatcher(Go.LOGIN_JSP)).thenReturn(requestDispatcher);

        login.doGet(request, response);


        verify(request).getRequestDispatcher(Go.LOGIN_JSP);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void whenUserPostCorrectLogin_thenRedirectProfile() throws IOException, ServletException {
        doNothing().when(session).setAttribute(eq(Key.USER), any(User.class));
        doNothing().when(response).sendRedirect(Go.LOGIN);

        when(request.getParameter(Key.USER_LOGIN)).thenReturn("admin");
        when(request.getParameter(Key.USER_PASSWORD)).thenReturn("admin");

        login.doPost(request, response);

        verify(request).getParameter(eq(Key.USER_LOGIN));
        verify(request).getParameter(eq(Key.USER_PASSWORD));
        verify(request).getSession();
        verify(response).sendRedirect(eq(Go.PROFILE));
    }

    @Test
    void whenUserPostIncorrectLogin_thenRedirectError() throws IOException, ServletException {
        doNothing().when(session).setAttribute(eq(Key.USER), any(User.class));
        doNothing().when(response).sendRedirect(Go.ERROR);

        when(request.getParameter(Key.USER_LOGIN)).thenReturn("admin");
        when(request.getParameter(Key.USER_PASSWORD)).thenReturn("password");

        doNothing().when(session).setAttribute(Key.ERROR, Key.ERROR_USER_NOT_FOUND);

        login.doPost(request, response);

        verify(request).getParameter(eq(Key.USER_LOGIN));
        verify(request).getParameter(eq(Key.USER_PASSWORD));
        verify(request).getSession();
        verify(response).sendRedirect(eq(Go.LOGIN));

        verify(session).setAttribute(eq(Key.ERROR), eq(Key.ERROR_USER_NOT_FOUND));
    }
}