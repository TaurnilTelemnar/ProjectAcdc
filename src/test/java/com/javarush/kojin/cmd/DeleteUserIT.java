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

class DeleteUserIT extends BaseIT {
    private final DeleteUser deleteUser = (DeleteUser) Components.getCommand(Go.DELETE_USER);

    @Test
    void whenGetUserFromRepo_thenRedirectError() throws ServletException, IOException {
        when(request.getParameter(Key.ID)).thenReturn(null);

        deleteUser.doGet(request, response);

        verify(request).getParameter(eq(Key.ID));
        verify(request).getSession();
        verify(session).setAttribute(eq(Key.ERROR), eq(Key.ERROR_404));
        verify(response).sendRedirect(eq(Go.ERROR));
    }

    @Test
    void whenGetUserFromRepo_thenForwardDeleteUSerJsp() throws ServletException, IOException {
        when(request.getParameter(Key.ID)).thenReturn("1");
        when(request.getRequestDispatcher(Key.FORMAT_LINK_ID.formatted(Go.DELETE_USER_JSP, 1L))).thenReturn(requestDispatcher);

        deleteUser.doGet(request, response);

        verify(request, times(2)).getParameter(eq(Key.ID));
        verify(request).setAttribute(eq(Key.USER), any(User.class));
        verify(request).getRequestDispatcher(eq(Key.FORMAT_LINK_ID.formatted(Go.DELETE_USER_JSP, 1)));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void whenPostUserFromRepo_thenRedirectError() throws ServletException, IOException {
        when(request.getParameter(Key.ID)).thenReturn(null);

        deleteUser.doPost(request, response);

        verify(request).getParameter(eq(Key.ID));
        verify(request).getSession();
        verify(session).setAttribute(eq(Key.ERROR), eq(Key.ERROR_404));
        verify(response).sendRedirect(eq(Go.ERROR));
    }

    @Test
    void whenDeleteUser_thenUserDeleted() throws ServletException, IOException {
        when(request.getParameter(Key.ID)).thenReturn("1");
        when(request.getParameter(Go.DELETE_USER)).thenReturn(Go.DELETE_USER);

        deleteUser.doPost(request, response);

        verify(request, times(2)).getParameter(eq(Key.ID));
        verify(request).getParameter(eq(Go.DELETE_USER));
        verify(response).sendRedirect(eq(Go.HOME));
    }

    @Test
    void whenDeleteUserWithoutParam_thenRedirectHome() throws ServletException, IOException {
        when(request.getParameter(Key.ID)).thenReturn("2");
        when(request.getParameter(Go.DELETE_USER)).thenReturn(null);

        deleteUser.doPost(request, response);

        verify(request, times(2)).getParameter(eq(Key.ID));
        verify(request).getParameter(eq(Go.DELETE_USER));
        verify(response).sendRedirect(eq(Go.HOME));
    }

}