package com.javarush.kojin.cmd;

import com.javarush.kojin.BaseIT;
import com.javarush.kojin.config.Components;
import com.javarush.kojin.service.UserService;
import com.javarush.kojin.util.Go;
import com.javarush.kojin.util.Key;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;
class ListUserIT extends BaseIT {
    private final ListUser listUser = (ListUser) Components.getCommand(Go.LIST_USER);
    @Test
    void whenGetListUser_thenForwardListUserJsp() throws ServletException, IOException {
        when(request.getRequestDispatcher(Go.LIST_USER_JSP)).thenReturn(requestDispatcher);

        listUser.doGet(request, response);

        verify(request).setAttribute(eq(Key.USERS), eq(Components.getComponent(UserService.class).getAllUsers()));
        verify(request).getRequestDispatcher(eq(Go.LIST_USER_JSP));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void whenPostListUser_thenRedirectListUser() throws ServletException, IOException {
        listUser.doPost(request, response);

        verify(response).sendRedirect(eq(Go.LIST_USER));
    }
}