package com.javarush.kojin.cmd;

import com.javarush.kojin.BaseIT;
import com.javarush.kojin.config.Components;
import com.javarush.kojin.util.Go;
import com.javarush.kojin.util.Key;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class ProfileIT extends BaseIT {
    private final Profile profile = (Profile) Components.getCommand(Go.PROFILE);
    @Test
    void whenUserGetProfile_thenForwardProfileJsp() throws ServletException, IOException {
        when(request.getRequestDispatcher(Go.PROFILE_JSP)).thenReturn(requestDispatcher);

        profile.doGet(request, response);

        verify(request).getRequestDispatcher(eq(Go.PROFILE_JSP));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void whenUserGoEditUser_thenRedirectEditUser() throws IOException, ServletException {
        when(request.getParameter(Go.EDIT_USER)).thenReturn(Go.EDIT_USER);
        when(request.getParameter(Key.ID)).thenReturn("1");
        doNothing().when(response).sendRedirect(Key.FORMAT_LINK_ID.formatted(Go.EDIT_USER, 1L));

        profile.doPost(request, response);

        verify(request).getParameter(eq(Go.EDIT_USER));
        verify(request).getParameter(eq(Key.ID));
        verify(response).sendRedirect(Key.FORMAT_LINK_ID.formatted(Go.EDIT_USER, 1L));
    }

    @Test
    void whenUserGoLogout_thenRedirectLogout() throws IOException, ServletException {
        when(request.getParameter(Go.EDIT_USER)).thenReturn(null);
        when(request.getParameter(Go.LOGOUT)).thenReturn(Go.LOGOUT);
        doNothing().when(response).sendRedirect(Go.LOGOUT);

        profile.doPost(request, response);

        verify(request).getParameter(eq(Go.EDIT_USER));
        verify(request).getParameter(eq(Go.LOGOUT));
        verify(response).sendRedirect(eq(Go.LOGOUT));
    }
}
