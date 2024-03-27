package com.javarush.kojin.cmd;

import com.javarush.kojin.util.Go;
import com.javarush.kojin.util.Key;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class Error implements Command{
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if(session.getAttribute(Key.ERROR) != null){
            RequestDispatcher requestDispatcherErrorJsp = req.getRequestDispatcher(Go.ERROR_JSP);
            requestDispatcherErrorJsp.forward(req, resp);
        }else {
            resp.sendRedirect(Go.HOME);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if(session.getAttribute(Key.ERROR) != null){
            session.setAttribute(Key.ERROR, null);
        }
        resp.sendRedirect(Go.HOME);
    }
}
