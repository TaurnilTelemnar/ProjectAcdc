package com.javarush.kojin.service;

import com.javarush.kojin.entity.Quest;
import com.javarush.kojin.repository.QuestRepository;

public class QuestService {
    private final QuestRepository questRepository;

    public QuestService(QuestRepository questRepository) {
        this.questRepository = questRepository;
    }

    public void createQuest(Quest quest) {
        questRepository.create(quest);
    }

    public void updateQuest(Quest quest) {
        questRepository.update(quest);
    }
}
