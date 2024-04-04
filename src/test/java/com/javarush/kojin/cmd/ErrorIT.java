package com.javarush.kojin.cmd;

import com.javarush.kojin.BaseIT;
import com.javarush.kojin.config.Components;
import com.javarush.kojin.util.Go;
import com.javarush.kojin.util.Key;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

class ErrorIT extends BaseIT {
    private final Error error = (Error) Components.getCommand(Go.ERROR);

    @Test
    void whenSessionHasError_thenForwardErrorJsp() throws ServletException, IOException {
        when(session.getAttribute(Key.ERROR)).thenReturn(Key.ERROR);
        when(request.getRequestDispatcher(Go.ERROR_JSP)).thenReturn(requestDispatcher);

        error.doGet(request, response);

        verify(request).getSession();
        verify(session).getAttribute(eq(Key.ERROR));
        verify(request).getRequestDispatcher(eq(Go.ERROR_JSP));
        verify(requestDispatcher).forward(request, response);
    }
    @Test
    void whenSessionWithoutError_thenRedirectHome() throws ServletException, IOException {
        when(session.getAttribute(Key.ERROR)).thenReturn(null);

        error.doGet(request, response);

        verify(request).getSession();
        verify(session).getAttribute(eq(Key.ERROR));
        verify(response).sendRedirect(eq(Go.HOME));
    }

    @Test
    void whenPostErrorWithErrorInSession_thenErrorDeletedFromSession() throws ServletException, IOException {
        when(session.getAttribute(Key.ERROR)).thenReturn(Key.ERROR);

        error.doPost(request, response);

        verify(request).getSession();
        verify(session).getAttribute(eq(Key.ERROR));
        verify(session).setAttribute(eq(Key.ERROR), eq(null));
        verify(response).sendRedirect(eq(Go.HOME));
    }

    @Test
    void whenPostErrorWithoutErrorInSession_thenRedirectHome() throws ServletException, IOException {
        when(session.getAttribute(Key.ERROR)).thenReturn(null);

        error.doPost(request, response);

        verify(request).getSession();
        verify(session).getAttribute(eq(Key.ERROR));
        verify(response).sendRedirect(eq(Go.HOME));
    }
}