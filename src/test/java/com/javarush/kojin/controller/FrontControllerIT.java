package com.javarush.kojin.controller;

import com.javarush.kojin.BaseIT;
import com.javarush.kojin.entity.GameState;
import com.javarush.kojin.entity.Role;
import com.javarush.kojin.util.Go;
import com.javarush.kojin.util.Key;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class FrontControllerIT extends BaseIT {
    ServletConfig servletConfig = mock(ServletConfig.class);
    ServletContext servletContext = mock(ServletContext.class);

    @Test
    void whenSendIncorrectMethod_thenGiveException() {
        FrontController frontController = new FrontController();
        when(request.getMethod()).thenReturn("incorrect Method");
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(request.getRequestURI()).thenReturn(Go.HOME);

        frontController.init(servletConfig);
        verify(servletConfig, times(2)).getServletContext();
        verify(servletContext).setAttribute(eq(Key.ROLES), eq(Role.values()));
        verify(servletContext).setAttribute(eq(Key.GAME_STATES), eq(GameState.values()));


        assertThrows(UnsupportedOperationException.class, () -> frontController.service(request, response));
        verify(request, times(3)).getMethod();
    }

    @Test
    void whenSendGetMethod_thenRinGetMethodFromCommand() throws ServletException, IOException {
        FrontController frontController = new FrontController();
        when(request.getMethod()).thenReturn("get");
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(request.getRequestURI()).thenReturn(Go.SIGNUP);
        when(request.getRequestDispatcher(Go.SIGNUP_JSP)).thenReturn(requestDispatcher);

        frontController.init(servletConfig);
        verify(servletConfig, times(2)).getServletContext();
        verify(servletContext).setAttribute(eq(Key.ROLES), eq(Role.values()));
        verify(servletContext).setAttribute(eq(Key.GAME_STATES), eq(GameState.values()));

        frontController.service(request, response);
        verify(request).getRequestDispatcher(eq(Go.SIGNUP_JSP));
        verify(requestDispatcher).forward(request, response);
        verify(request).getMethod();

    }

    @Test
    void whenSendPostMethod_thenRinPostMethodFromCommand() throws ServletException, IOException {
        FrontController frontController = new FrontController();
        when(request.getMethod()).thenReturn("post");
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(request.getRequestURI()).thenReturn(Go.PROFILE);

        frontController.init(servletConfig);
        verify(servletConfig, times(2)).getServletContext();
        verify(servletContext).setAttribute(eq(Key.ROLES), eq(Role.values()));
        verify(servletContext).setAttribute(eq(Key.GAME_STATES), eq(GameState.values()));

        frontController.service(request, response);

        verify(request, times(2)).getMethod();
    }

}