package com.javarush.kojin.cmd;

import com.javarush.kojin.entity.User;
import com.javarush.kojin.service.UserService;
import com.javarush.kojin.util.Go;
import com.javarush.kojin.util.Key;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


public class DeleteUser implements Command{
    private final UserService userService;

    public DeleteUser(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = getUserFromRepoById(req, userService);
        if(user == null){
            errorNotFound(req, resp);
            return;
        }
        req.setAttribute(Key.USER, user);
        RequestDispatcher requestDispatcherDeleteUserJsp = req.getRequestDispatcher(Key.FORMAT_LINK_ID.formatted(Go.DELETE_USER_JSP, user.getId()));
        requestDispatcherDeleteUserJsp.forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getUserFromRepoById(req, userService);
        if(user == null){
            errorNotFound(req, resp);
            return;
        }

        if(req.getParameter(Go.DELETE_USER) != null){
            userService.deleteUser(user.getId());
        }
        resp.sendRedirect(Go.HOME);
    }

}
