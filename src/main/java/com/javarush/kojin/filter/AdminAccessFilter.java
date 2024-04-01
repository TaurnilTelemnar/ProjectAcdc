package com.javarush.kojin.filter;

import com.javarush.kojin.entity.Role;
import com.javarush.kojin.entity.User;
import com.javarush.kojin.util.Go;
import com.javarush.kojin.util.Key;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminAccessFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(Key.USER);

        if (user.getRole().equals(Role.USER)) {
            switch (getCommandName(req)){
                case Go.EDIT_USER, Go.DELETE_QUEST, Go.EDIT_QUEST -> {
                    if(isUserDenied(req, user)){
                        sendErrorNoPermissions(session, res);
                    } else {
                        chain.doFilter(req, res);
                    }
                    return;
                }
            }
        }



        if(!user.getRole().equals(Role.ADMIN)){
            session.setAttribute(Key.ERROR, Key.ERROR_NO_PERMISSIONS);
            res.sendRedirect(Go.ERROR);
        } else {
            chain.doFilter(req, res);
        }
    }

    private boolean isUserDenied(HttpServletRequest req, User user) {
        Long requestId = Long.parseLong(req.getParameter(Key.ID));
        return !requestId.equals(user.getId());
    }

    private String getCommandName(HttpServletRequest req){
        String uri = req.getRequestURI();
        Matcher matcher = Pattern.compile("[a-z-]+").matcher(uri);
        String cmdName = Go.HOME;

        if (matcher.find()) {
            cmdName = "/%s".formatted(matcher.group());
        }
        return cmdName;
    }

    private void sendErrorNoPermissions(HttpSession session, HttpServletResponse res) throws IOException {
        session.setAttribute(Key.ERROR, Key.ERROR_NO_PERMISSIONS);
        res.sendRedirect(Go.ERROR);
    }
}
