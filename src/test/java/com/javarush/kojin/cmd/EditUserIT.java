package com.javarush.kojin.cmd;

import com.javarush.kojin.BaseIT;
import com.javarush.kojin.config.Components;
import com.javarush.kojin.entity.Role;
import com.javarush.kojin.entity.User;
import com.javarush.kojin.repository.UserRepository;
import com.javarush.kojin.util.Go;
import com.javarush.kojin.util.Key;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EditUserIT extends BaseIT {
    private final EditUser editUser = (EditUser) Components.getCommand(Go.EDIT_USER);

    @Test
    void whenGetUnExistingUser_thenRedirectError() throws ServletException, IOException {
        when(request.getParameter(Key.ID)).thenReturn(null);

        editUser.doGet(request, response);

        verify(request).getParameter(eq(Key.ID));
        verify(request).getSession();
        verify(session).setAttribute(eq(Key.ERROR), eq(Key.ERROR_404));
        verify(response).sendRedirect(eq(Go.ERROR));
    }
    @Test
    void whenGetExistingUser_thenForwardEditUserJsp() throws ServletException, IOException {
        when(request.getParameter(Key.ID)).thenReturn("1");
        when(request.getRequestDispatcher(Key.FORMAT_LINK_ID.formatted(Go.EDIT_USER_JSP, 1))).thenReturn(requestDispatcher);

        editUser.doGet(request, response);

        verify(request, times(2)).getParameter(eq(Key.ID));
        verify(request).setAttribute(eq(Key.USER), any(User.class));
        verify(request).getRequestDispatcher(eq(Key.FORMAT_LINK_ID.formatted(Go.EDIT_USER_JSP, 1)));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void whenPostUnExistingUser_thenRedirectError() throws ServletException, IOException {
        when(request.getParameter(Key.ID)).thenReturn(null);

        editUser.doPost(request, response);

        verify(request).getParameter(eq(Key.ID));
        verify(request).getSession();
        verify(session).setAttribute(eq(Key.ERROR), eq(Key.ERROR_404));
        verify(response).sendRedirect(eq(Go.ERROR));
    }

    @Test
    void whenPostExistingUserFromAdmin_thenUserEdit() throws ServletException, IOException {
        User admin = Components.getComponent(UserRepository.class).get(1L);
        User user = Components.getComponent(UserRepository.class).get(2L);
        when(request.getParameter(Key.ID)).thenReturn("2");
        String userNewName = "User New Name";
        when(request.getParameter(Key.USER_NAME)).thenReturn(userNewName);
        String userNewLogin = "User New Login";
        when(request.getParameter(Key.USER_LOGIN)).thenReturn(userNewLogin);
        String userNewPassword = "User New Password";
        when(request.getParameter(Key.USER_PASSWORD)).thenReturn(userNewPassword);
        Role userRole = Role.ADMIN;
        when(request.getParameter(Key.USER_ROLE)).thenReturn(userRole.name());
        when(session.getAttribute(Key.USER)).thenReturn(admin);

        editUser.doPost(request, response);

        verify(request, times(2)).getParameter(eq(Key.ID));
        verify(request).getSession();
        verify(session).getAttribute(eq(Key.USER));
        verify(request).getParameter(eq(Key.USER_NAME));
        verify(request).getParameter(eq(Key.USER_LOGIN));
        verify(request).getParameter(eq(Key.USER_PASSWORD));
        verify(request).getParameter(eq(Key.USER_ROLE));
        verify(response).sendRedirect(eq(Key.FORMAT_LINK_ID.formatted(Go.EDIT_USER, 2)));

        assertEquals(user.getName(), userNewName);
        assertEquals(user.getLogin(), userNewLogin);
        assertEquals(user.getPassword(), userNewPassword);
        assertEquals(user.getRole(), userRole);

    }

    @Test
    void whenPostExistingUserFromUser_thenUserEdit() throws ServletException, IOException {
        User user = Components.getComponent(UserRepository.class).get(2L);
        User editableUser = Components.getComponent(UserRepository.class).get(2L);
        when(request.getParameter(Key.ID)).thenReturn("2");
        String userNewName = "User New Name";
        when(request.getParameter(Key.USER_NAME)).thenReturn(userNewName);
        String userNewLogin = "User New Login";
        when(request.getParameter(Key.USER_LOGIN)).thenReturn(userNewLogin);
        String userNewPassword = "User New Password";
        when(request.getParameter(Key.USER_PASSWORD)).thenReturn(userNewPassword);
        Role userRole = Role.ADMIN;
        when(request.getParameter(Key.USER_ROLE)).thenReturn(userRole.name());
        when(session.getAttribute(Key.USER)).thenReturn(user);

        editUser.doPost(request, response);

        verify(request, times(2)).getParameter(eq(Key.ID));
        verify(request).getSession();
        verify(session).getAttribute(eq(Key.USER));
        verify(request).getParameter(eq(Key.USER_NAME));
        verify(request).getParameter(eq(Key.USER_LOGIN));
        verify(request).getParameter(eq(Key.USER_PASSWORD));
        verify(response).sendRedirect(eq(Key.FORMAT_LINK_ID.formatted(Go.EDIT_USER, 2)));

        assertEquals(editableUser.getName(), userNewName);
        assertEquals(editableUser.getLogin(), userNewLogin);
        assertEquals(editableUser.getPassword(), userNewPassword);

    }
}