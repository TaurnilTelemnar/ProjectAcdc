package com.javarush.kojin.cmd;

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
import java.util.Optional;

public class Login implements Command{

    private final UserService userService;

    public Login(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcherLoginJsp = req.getRequestDispatcher(Go.LOGIN_JSP);
        requestDispatcherLoginJsp.forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getUserFromRequest(req, userService);
        HttpSession session = req.getSession();
        if(user != null){
            session.setAttribute(Key.USER, user);
            resp.sendRedirect(Go.PROFILE);
        }else {
            session.setAttribute(Key.ERROR, Key.ERROR_USER_NOT_FOUND);
            resp.sendRedirect(Go.LOGIN);
        }
    }
//    private User getUserFromRequest(HttpServletRequest req, UserService userService){
//        User pattern = User.builder()
//                .login(req.getParameter(Key.USER_LOGIN))
//                .password(req.getParameter(Key.USER_PASSWORD))
//                .build();
//        Optional<User> optionalUser = userService.getUser(pattern);
//        return optionalUser.orElse(null);
//    }


}
