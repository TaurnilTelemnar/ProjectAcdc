package com.javarush.kojin.repository;


import com.javarush.kojin.entity.Quest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class QuestRepositoryTest {
    QuestRepository questRepository;
    Quest requiredQuest;
    Quest testQuest;
    @BeforeEach
    void init(){
        questRepository = new QuestRepository();
        requiredQuest = Quest.builder()
                .name("Ынопланетный")
                .build();
        testQuest = Quest.builder()
                .name("Тестовый квест")
                .build();
        questRepository.create(testQuest);
        questRepository.create(requiredQuest);

    }
    @Test
    void whenQuestFindByNameThenReturnsQuestFromRepo() {
        Quest pattern = Quest.builder()
                .name("Ынопланетный")
                .build();

        Optional<Quest> testObject = questRepository.find(pattern).findFirst();
        assertTrue(testObject.isPresent());
        String questName = requiredQuest.getName();
        String testQuestName = testObject.get().getName();
        assertEquals(questName, testQuestName);
    }

    @Test
    void whenCreateQuestThenQuestFromRepoHasId(){
        Quest testQuest = Quest.builder()
                .name("newQuest")
                .build();
        questRepository.create(testQuest);
        assertNotNull(testQuest.getId());
    }

    @Test
    void whenGetQuestFromRepoThenReturnsCorrectQuest(){
        Long questId = requiredQuest.getId();

        Quest testQuest = questRepository.get(questId);
        assertEquals(requiredQuest, testQuest);
    }

    @Test
    void whenGetAllQuestsThenTakenQuestsAreMatches(){
        Collection<Quest> allQuests = questRepository.getAll();
        assertTrue(allQuests.contains(requiredQuest));
        assertTrue(allQuests.contains(testQuest));

    }

    @Test
    void whenDeleteQuestThenTakenNull() {
        Long questId = testQuest.getId();
        questRepository.delete(testQuest.getId());

        Quest testQuest = questRepository.get(questId);
        assertNull(testQuest);
    }
}