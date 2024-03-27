package com.javarush.kojin.cmd;

import com.javarush.kojin.entity.User;
import com.javarush.kojin.service.UserService;
import com.javarush.kojin.util.Go;
import com.javarush.kojin.util.Key;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

public interface Command {

    void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;


    void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

    default void errorUserAlreadyExists(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        session.setAttribute(Key.ERROR, Key.ERROR_USER_ALREADY_EXISTS);
        resp.sendRedirect(Go.SIGNUP);
    }

    default User getUserFromRequest(HttpServletRequest req, UserService userService) {
        User pattern = User.builder()
                .login(req.getParameter(Key.USER_LOGIN))
                .password(req.getParameter(Key.USER_PASSWORD))
                .build();
        Optional<User> optionalUser = userService.getUser(pattern);
        return optionalUser.orElse(null);
    }


    default void errorUserNotFound(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        session.setAttribute(Key.ERROR, Key.ERROR_404);
        resp.sendRedirect(Go.ERROR);
    }

    default boolean isUserLoginAlreadyExists(String userLogin, UserService userService) throws IOException {
        User pattern = User.builder()
                .login(userLogin)
                .build();
        return userService.getUser(pattern).isPresent();
    }

    default Long getIdFromRequest(HttpServletRequest req){
        String stringId = req.getParameter(Key.ID);
        return stringId != null
                ? Long.parseLong(stringId)
                : null;
    }

    default boolean isIdFromRequestExists(HttpServletRequest req){
        return getIdFromRequest(req) != null;
    }

    default User getUserFromRepoById(HttpServletRequest req, UserService userService){
        if(!isIdFromRequestExists(req)){
            return null;
        }
        Optional<User> optionalUser = userService.getUser(getIdFromRequest(req));
        return optionalUser.orElse(null);
    }
}
