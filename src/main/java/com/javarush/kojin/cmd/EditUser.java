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


public class EditUser implements Command{
    private final UserService userService;

    public EditUser(UserService userService) {
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

        RequestDispatcher requestDispatcherEditUserJsp = req.getRequestDispatcher(Key.FORMAT_LINK_ID.formatted(Go.EDIT_USER_JSP, user.getId()));
        requestDispatcherEditUserJsp.forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User editableUser = getUserFromRepoById(req, userService);

        if(editableUser == null){
            errorNotFound(req, resp);
            return;
        }

        HttpSession session = req.getSession();
        User sessionUser = (User) session.getAttribute(Key.USER);

        editableUser.setName(req.getParameter(Key.USER_NAME));
        editableUser.setLogin(req.getParameter(Key.USER_LOGIN));
        editableUser.setPassword(req.getParameter(Key.USER_PASSWORD));

        if(sessionUser.getRole().equals(Role.ADMIN)){
            editableUser.setRole(Role.valueOf(req.getParameter(Key.USER_ROLE)));
        }


        userService.updateUser(editableUser);
        resp.sendRedirect(Key.FORMAT_LINK_ID.formatted(Go.EDIT_USER, editableUser.getId()));
    }
}
