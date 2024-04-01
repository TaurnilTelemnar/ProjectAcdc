package com.javarush.kojin.cmd;

import com.javarush.kojin.util.Go;
import com.javarush.kojin.util.Key;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class Profile implements Command{

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcherProfileJsp = req.getRequestDispatcher(Go.PROFILE_JSP);
        requestDispatcherProfileJsp.forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter(Go.EDIT_USER) != null){
            resp.sendRedirect(Key.FORMAT_LINK_ID.formatted(Go.EDIT_USER, Long.parseLong(req.getParameter(Key.ID))));
        }else if(req.getParameter(Go.LOGOUT) != null){
            resp.sendRedirect(Go.LOGOUT);
        }
    }
}
