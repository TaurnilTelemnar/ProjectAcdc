package com.javarush.kojin.filter;

import com.javarush.kojin.cmd.Command;
import com.javarush.kojin.config.Components;
import com.javarush.kojin.controller.HttpResolver;
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

public class UnknownPageFilter extends HttpFilter {
    private HttpResolver httpResolver;

    @Override
    public void init() {
        httpResolver = Components.getComponent(HttpResolver.class);
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String commandName = getCurrentCommandName(req);
        Command resolveCommandName = httpResolver.resolve(commandName);
        if (resolveCommandName != null) {
            chain.doFilter(req, res);
        } else if (commandName != null && commandName.equals("/")) {
            chain.doFilter(req, res);
        } else {
            HttpSession session = req.getSession();
            session.setAttribute(Key.ERROR, Key.ERROR_404);
            res.sendRedirect(Go.ERROR);
        }
    }

    private String getCurrentCommandName(HttpServletRequest req) {
        String uri = req.getRequestURI();
        Matcher matcher = Pattern.compile("[a-z-]+").matcher(uri);
        String cmdName = "/";
        if (matcher.find()) {
            cmdName = "/%s".formatted(matcher.group());
        }
        return cmdName;
    }
}
