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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebFilter({
        Go.HOME, Go.INDEX, Go.START,
        Go.PROFILE, Go.LOGOUT,
        Go.EDIT_USER, Go.LIST_USER,
        Go.DELETE_USER, Go.CREATE_USER
})
public class SecurityFilter extends HttpFilter {
    private UserService userService;

    @Override
    public void init() {
        userService = Components.getComponent(UserService.class);
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        if (isGeneralSecure(req, res, userService)
                && isEditUserSecure(req, res)
                 && isAdminSecure(req, res)) {
            chain.doFilter(req, res);
        }
    }

    private boolean isGeneralSecure(HttpServletRequest req, HttpServletResponse resp, UserService userService) throws IOException {
        if (!isUserExists(req, userService)) {
            User guest = createGuest();
            HttpSession session = req.getSession();
            session.setAttribute(Key.USER, guest);
            resp.sendRedirect(Go.LOGIN);
            return false;
        }
        return true;
    }

    private User getUserFromSession(HttpServletRequest req, UserService userService) {
        HttpSession session = req.getSession();
        Object rawUser = session.getAttribute(Key.USER);
        if (rawUser == null) return null;

        User user = (User) rawUser;
        if(user.getRole().equals(Role.GUEST)) return user;

        Optional<User> repoUser = userService.getUser(user);
        return repoUser.orElse(null);
    }

    private boolean isUserExists(HttpServletRequest req, UserService userService) {
        return getUserFromSession(req, userService) != null;
    }

    private User createGuest() {
        return User.builder()
                .name("Гость")
                .role(Role.GUEST)
                .build();
    }

    private String getCurrentCommandName(HttpServletRequest req){
        String uri = req.getRequestURI();
        Matcher matcher = Pattern.compile("[a-z-]+").matcher(uri);
        String cmdName = "";
        if(matcher.find()){
            cmdName = "/%s".formatted(matcher.group());
        }
        return cmdName;
    }

    private boolean isEditUserSecure(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!getCurrentCommandName(req).equals(Go.EDIT_USER)) return true;

        if(getRequestId(req) == null) {
            HttpSession session = req.getSession();
            sendError(session, resp, Key.ERROR_404);
            return false;
        }
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(Key.USER);
        switch (user.getRole()){
            case ADMIN -> {
                return true;
            }
            case GUEST -> {
                sendError(session, resp, Key.ERROR_NO_PERMISSIONS);
                return false;
            }
            case USER -> {
                Long requestId = getRequestId(req);
                if(user.getId().equals(requestId)){
                    return true;
                }else {
                    sendError(session, resp, Key.ERROR_NO_PERMISSIONS);
                    return false;
                }
            }
        }
        sendError(session, resp, Key.ERROR_UNKNOWN);
        return false;
    }

    private Long getRequestId(HttpServletRequest req){
        String stringId = req.getParameter(Key.ID);
        Long id = null;
        try{
            id = Long.parseLong(stringId);
        }catch (RuntimeException ignored){}
        return id;
    }

    private void sendError(HttpSession session, HttpServletResponse resp, String error) throws IOException {
        session.setAttribute(Key.ERROR, error);
        resp.sendRedirect(Go.ERROR);
    }

    private boolean isAdminSecure(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if(!getCurrentCommandName(req).equals(Go.LIST_USER)
        && !getCurrentCommandName(req).equals(Go.DELETE_USER)
        && !getCurrentCommandName(req).equals(Go.CREATE_USER)) return true;


        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(Key.USER);

        if(user.getRole().equals(Role.ADMIN)) return true;

        sendError(session, resp,Key.ERROR_NO_PERMISSIONS);
        return false;
    }
}
