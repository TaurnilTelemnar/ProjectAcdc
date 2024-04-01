package com.javarush.kojin.controller;

import com.javarush.kojin.cmd.Command;
import com.javarush.kojin.config.Components;
import com.javarush.kojin.config.Config;
import com.javarush.kojin.entity.GameState;
import com.javarush.kojin.entity.Role;
import com.javarush.kojin.util.Go;
import com.javarush.kojin.util.Key;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet({
        Go.INDEX, Go.HOME, Go.START,
        Go.LOGIN, Go.PROFILE, Go.LOGOUT,
        Go.EDIT_USER, Go.ERROR,
        Go.LIST_USER, Go.DELETE_USER,
        Go.SIGNUP, Go.CREATE_USER,
        Go.CREATE_QUEST, Go.DELETE_QUEST,
        Go.EDIT_QUEST, Go.PLAY
})
public class FrontController extends HttpServlet {

    private HttpResolver httpResolver;

    @Override
    public void init(ServletConfig config) {
        httpResolver = Components.getComponent(HttpResolver.class);
        Objects.requireNonNull(Components.getComponent(Config.class)).addStartData();
        config.getServletContext().setAttribute(Key.ROLES, Role.values());
        config.getServletContext().setAttribute(Key.GAME_STATES, GameState.values());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        Matcher matcher = Pattern.compile("[a-z-]+").matcher(uri);
        String cmdName = Go.HOME;

        if (matcher.find()) {
            cmdName = "/%s".formatted(matcher.group());
        }

        Command command = httpResolver.resolve(cmdName);

        if(req.getMethod().equalsIgnoreCase("get")){
            command.doGet(req, resp);
        } else if(req.getMethod().equalsIgnoreCase("post")){
            command.doPost(req, resp);
        } else {
            throw new UnsupportedOperationException(req.getMethod());
        }
    }
}
