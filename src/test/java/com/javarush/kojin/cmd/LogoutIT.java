package com.javarush.kojin.cmd;

import com.javarush.kojin.BaseIT;
import com.javarush.kojin.config.Components;
import com.javarush.kojin.util.Go;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

class LogoutIT extends BaseIT {
    private final Logout logout = (Logout) Components.getCommand(Go.LOGOUT);

    @Test
    void whenGetLogout_thenForwardLogoutJsp() throws ServletException, IOException {
        when(request.getRequestDispatcher(Go.LOGOUT_JSP)).thenReturn(requestDispatcher);

        logout.doGet(request, response);

        verify(request).getRequestDispatcher(eq(Go.LOGOUT_JSP));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void whenPostLogout_thenLogoutAndRedirectHome() throws ServletException, IOException {
        when(request.getParameter(Go.LOGOUT)).thenReturn(Go.LOGOUT);

        logout.doPost(request, response);

        verify(request).getParameter(eq(Go.LOGOUT));
        verify(request).getSession();
        verify(session).invalidate();
        verify(response).sendRedirect(Go.HOME);
    }

    @Test
    void whenPostWithoutLogout_thenRedirectHome() throws ServletException, IOException {
        when(request.getParameter(Go.LOGOUT)).thenReturn(null);

        logout.doPost(request, response);

        verify(request).getParameter(eq(Go.LOGOUT));
        verify(response).sendRedirect(Go.HOME);
    }
}