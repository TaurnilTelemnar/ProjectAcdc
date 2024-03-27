package com.javarush.kojin.cmd;

import com.javarush.kojin.util.Go;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class Logout implements Command{

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcherLogoutJsp = req.getRequestDispatcher(Go.LOGOUT_JSP);
        requestDispatcherLogoutJsp.forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter(Go.LOGOUT) != null){
            HttpSession session = req.getSession();
            session.invalidate();
        }
        resp.sendRedirect(Go.HOME);
    }
}
