package com.javarush.kojin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Game implements AbstractEntity{
    private Long id;
    private Long questId;
    private Long userId;
    private GameState gameState;
    private Long currentQuestionId;
}
