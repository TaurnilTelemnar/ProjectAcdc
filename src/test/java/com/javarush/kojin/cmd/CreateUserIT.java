package com.javarush.kojin.cmd;

import com.javarush.kojin.BaseIT;
import com.javarush.kojin.config.Components;
import com.javarush.kojin.entity.Role;
import com.javarush.kojin.util.Go;
import com.javarush.kojin.util.Key;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

class CreateUserIT extends BaseIT {
    private final CreateUser createUser = (CreateUser) Components.getCommand(Go.CREATE_USER);

    @Test
    void whenUserGetCreateUser_thenForwardCreateUserJsp() throws ServletException, IOException {
        when(request.getRequestDispatcher(Go.CREATE_USER_JSP)).thenReturn(requestDispatcher);
        doNothing().when(requestDispatcher).forward(request, response);

        createUser.doGet(request, response);

        verify(request).getRequestDispatcher(eq(Go.CREATE_USER_JSP));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void whenCreateUserLoginAlreadyExists_thenRedirectError() throws IOException, ServletException {
        when(request.getParameter(Key.USER_LOGIN)).thenReturn("admin");
        doNothing().when(session).setAttribute(Key.ERROR, Key.ERROR_USER_ALREADY_EXISTS);
        doNothing().when(response).sendRedirect(Go.SIGNUP);

        createUser.doPost(request, response);

        verify(request).getParameter(eq(Key.USER_LOGIN));
        verify(request).getSession();
        verify(session).setAttribute(eq(Key.ERROR), eq(Key.ERROR_USER_ALREADY_EXISTS));
        verify(response).sendRedirect(eq(Go.SIGNUP));
    }

    @Test
    void whenCreateUserWithCorrectLoginAndIncorrectRole_thenAccountCreates() throws IOException, ServletException {
        when(request.getParameter(Key.USER_LOGIN)).thenReturn("newUser");
        when(request.getParameter(Key.USER_PASSWORD)).thenReturn("newPassword");
        when(request.getParameter(Key.USER_NAME)).thenReturn("New User");
        when(request.getParameter(Key.USER_ROLE)).thenReturn(Role.GUEST.name());

        doNothing().when(response).sendRedirect(Key.FORMAT_LINK_ID.formatted(Go.EDIT_USER, any(Long.class)));

        createUser.doPost(request, response);

        verify(request, times(2)).getParameter(eq(Key.USER_LOGIN));
        verify(request).getParameter(eq(Key.USER_PASSWORD));
        verify(request).getParameter(eq(Key.USER_NAME));
        verify(request).getParameter(eq(Key.USER_ROLE));
    }

    @Test
    void whenCreateUserWithCorrectLoginAndCorrectRole_thenAccountCreates() throws IOException, ServletException {
        when(request.getParameter(Key.USER_LOGIN)).thenReturn("newRoleUser");
        when(request.getParameter(Key.USER_PASSWORD)).thenReturn("newRolePassword");
        when(request.getParameter(Key.USER_NAME)).thenReturn("New Role User");
        when(request.getParameter(Key.USER_ROLE)).thenReturn(Role.USER.name());

        doNothing().when(response).sendRedirect(Key.FORMAT_LINK_ID.formatted(Go.EDIT_USER, any(Long.class)));

        createUser.doPost(request, response);

        verify(request, times(2)).getParameter(eq(Key.USER_LOGIN));
        verify(request).getParameter(eq(Key.USER_PASSWORD));
        verify(request).getParameter(eq(Key.USER_NAME));
        verify(request).getParameter(eq(Key.USER_ROLE));
    }
}