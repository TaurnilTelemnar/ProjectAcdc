package com.javarush.kojin.service;

import com.javarush.kojin.entity.Quest;
import com.javarush.kojin.repository.QuestRepository;

import java.util.Collection;
import java.util.Optional;

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

    public Collection<Quest> getAll() {
        return questRepository.getAll();
    }

    public Optional<Quest> getQuest(Long questId) {
        Quest pattern = Quest.builder()
                .id(questId)
                .build();
        return questRepository.find(pattern).findFirst();
    }

    public void deleteQuest(Long questId) {
        questRepository.delete(questId);
    }
}
