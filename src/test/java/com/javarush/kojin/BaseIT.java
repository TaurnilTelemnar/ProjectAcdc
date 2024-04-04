package com.javarush.kojin;

import com.javarush.kojin.config.Components;
import com.javarush.kojin.config.Config;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;

import static org.mockito.Mockito.*;


public class BaseIT {
    protected final HttpServletRequest request;
    protected final RequestDispatcher requestDispatcher;
    protected final HttpServletResponse response;
    protected final HttpSession session;
    protected final Config config;

    @SneakyThrows
    protected BaseIT(){
        config = Components.getComponent(Config.class);
        config.addStartData();
        request = mock(HttpServletRequest.class);
        requestDispatcher = mock(RequestDispatcher.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        doNothing().when(requestDispatcher).forward(request, response);
    }
    @AfterAll
    static void reload(){
        Components.reLoad();
    }

}
