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
public class UserAccessFilter extends HttpFilter {


    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(Key.USER);

        if (!user.getRole().equals(Role.USER)) {
            chain.doFilter(req, res);
        } else {
            Long requestId = Long.parseLong(req.getParameter(Key.ID));
            if (requestId.equals(user.getId())) {
                chain.doFilter(req, res);
            } else {
                session.setAttribute(Key.ERROR, Key.ERROR_NO_PERMISSIONS);
                res.sendRedirect(Go.ERROR);
            }
        }
    }
}



