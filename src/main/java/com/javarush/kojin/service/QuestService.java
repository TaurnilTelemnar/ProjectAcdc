package com.javarush.kojin.service;

import com.javarush.kojin.repository.QuestRepository;

public class QuestService {
    private final QuestRepository questRepository;

    public QuestService(QuestRepository questRepository) {
        this.questRepository = questRepository;
    }
}
