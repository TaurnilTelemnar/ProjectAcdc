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

import java.io.IOException;
import java.util.ArrayList;

public class CreateUser implements Command{
    private final UserService userService;

    public CreateUser(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcherCreateUserJsp = req.getRequestDispatcher(Go.CREATE_USER_JSP);
        requestDispatcherCreateUserJsp.forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(isUserLoginAlreadyExists(req.getParameter(Key.USER_LOGIN), userService)){
            errorUserAlreadyExists(req, resp);
            return;
        }
        Role userRole = Role.valueOf(req.getParameter(Key.USER_ROLE));
        userRole = userRole.equals(Role.GUEST)
                ? Role.USER
                : userRole;
        User user = User.builder()
                .name(req.getParameter(Key.USER_NAME))
                .login(req.getParameter(Key.USER_LOGIN))
                .password(req.getParameter(Key.USER_PASSWORD))
                .role(userRole)
                .userQuests(new ArrayList<>())
                .build();


        userService.createUser(user);
        resp.sendRedirect(Key.FORMAT_LINK_ID.formatted(Go.EDIT_USER, user.getId()));
    }

}
