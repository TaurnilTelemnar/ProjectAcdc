package com.javarush.kojin.cmd;

import com.javarush.kojin.entity.Role;
import com.javarush.kojin.entity.User;
import com.javarush.kojin.service.UserService;
import com.javarush.kojin.util.Go;
import com.javarush.kojin.util.Key;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class Signup implements Command{
    private final UserService userService;

    public Signup(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcherSignup = req.getRequestDispatcher(Go.SIGNUP_JSP);
        requestDispatcherSignup.forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(isUserLoginAlreadyExists(req.getParameter(Key.USER_LOGIN), userService)){
            errorUserAlreadyExists(req, resp);
            return;
        }

        User user = User.builder()
                .name(req.getParameter(Key.USER_NAME))
                .login(req.getParameter(Key.USER_LOGIN))
                .password(req.getParameter(Key.USER_PASSWORD))
                .role(Role.USER)
                .build();
        userService.createUser(user);
        HttpSession session = req.getSession();
        session.invalidate();
        session = req.getSession();
        session.setAttribute(Key.USER, user);
        resp.sendRedirect(Go.PROFILE);
    }

}
