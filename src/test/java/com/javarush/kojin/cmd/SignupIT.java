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


public class SignupIT extends BaseIT {
    private final Signup signup = (Signup) Components.getCommand(Go.SIGNUP);

    @Test
    void whenUserGetSignup_thenForwardSignupJsp() throws ServletException, IOException {
        when(request.getRequestDispatcher(Go.SIGNUP_JSP)).thenReturn(requestDispatcher);

        signup.doGet(request, response);

        verify(request).getRequestDispatcher(Go.SIGNUP_JSP);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void whenUserPostLoginAlreadyExists_thenRedirectError() throws IOException, ServletException {
        when(request.getParameter(Key.USER_LOGIN)).thenReturn("admin");
        doNothing().when(session).setAttribute(Key.ERROR, Key.ERROR_USER_ALREADY_EXISTS);
        doNothing().when(response).sendRedirect(Go.SIGNUP);

        signup.doPost(request, response);

        verify(request).getParameter(eq(Key.USER_LOGIN));
        verify(request).getSession();
        verify(session).setAttribute(eq(Key.ERROR), eq(Key.ERROR_USER_ALREADY_EXISTS));
        verify(response).sendRedirect(eq(Go.SIGNUP));
    }

    @Test
    void whenUserPostCorrectLogin_thenAccountCreates() throws IOException, ServletException {
        when(request.getParameter(Key.USER_LOGIN)).thenReturn("newSignupUser");
        when(request.getParameter(Key.USER_PASSWORD)).thenReturn("newSignupPassword");
        when(request.getParameter(Key.USER_NAME)).thenReturn("New Signup User");
        doNothing().when(session).invalidate();
        doNothing().when(response).sendRedirect(Go.PROFILE);

        signup.doPost(request, response);

        verify(request, times(2)).getParameter(eq(Key.USER_LOGIN));
        verify(request).getParameter(eq(Key.USER_NAME));
        verify(request).getParameter(eq(Key.USER_PASSWORD));
        verify(request, times(2)).getSession();
        verify(session).invalidate();
        verify(session).setAttribute(eq(Key.USER), any(User.class));
        verify(response).sendRedirect(eq(Go.PROFILE));
    }
}
