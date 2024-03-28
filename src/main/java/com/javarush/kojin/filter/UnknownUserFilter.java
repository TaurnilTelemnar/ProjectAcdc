package com.javarush.kojin.filter;

import com.javarush.kojin.config.Components;
import com.javarush.kojin.entity.Role;
import com.javarush.kojin.entity.User;
import com.javarush.kojin.service.UserService;
import com.javarush.kojin.util.Go;
import com.javarush.kojin.util.Key;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;
public class UnknownUserFilter extends HttpFilter {
    private UserService userService;

    @Override
    public void init() {
        userService = Components.getComponent(UserService.class);
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpSession session = req.getSession();
        User user = getUserFromSession(session);

        if(user == null){
            User guest = User.builder()
                    .name("Гость")
                    .role(Role.GUEST)
                    .build();
            session.setAttribute(Key.USER, guest);
            res.sendRedirect(Go.LOGIN);
        } else {
            chain.doFilter(req, res);
        }


    }

    private User getUserFromSession(HttpSession session) {
        Object rawUser = session.getAttribute(Key.USER);
        if (rawUser == null) return null;

        User user = (User) rawUser;
        if(user.getRole().equals(Role.GUEST)) return user;

        Optional<User> repoUser = userService.getUser(user);
        return repoUser.orElse(null);
    }
}
