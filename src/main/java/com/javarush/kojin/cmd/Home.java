package com.javarush.kojin.cmd;

import com.javarush.kojin.entity.Quest;
import com.javarush.kojin.entity.Role;
import com.javarush.kojin.entity.User;
import com.javarush.kojin.service.QuestService;
import com.javarush.kojin.util.Go;
import com.javarush.kojin.util.Key;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

public class Home implements Command {
    private final QuestService questService;

    public Home(QuestService questService) {
        this.questService = questService;
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        Collection<Quest> allQuests = questService.getAll();
        Collection<Quest> quests;

        User currentUser = (User) session.getAttribute(Key.USER);

        quests = currentUser.getRole().equals(Role.GUEST)
                ? allQuests.stream()
                    .filter(q -> !q.getIsDraft())
                    .toList()
                : allQuests.stream()
                    .filter(q -> isQuestNeedToBeAdd(q, currentUser))
                    .toList();

        req.setAttribute(Key.QUESTS, quests);

        RequestDispatcher requestDispatcherHomeJsp = req.getRequestDispatcher(Go.HOME_JSP);
        requestDispatcherHomeJsp.forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(Go.HOME);
    }

    private boolean isQuestNeedToBeAdd(Quest quest, User user){
        if(quest.getIsDraft()){
            return quest.getAuthorId().equals(user.getId());
        } else {
            return true;
        }
    }
}
