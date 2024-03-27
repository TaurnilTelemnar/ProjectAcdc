package com.javarush.kojin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Answer implements AbstractEntity{
    private Long id;
    private Long questionId;
    private Long nextQuestionId;
    private String text;
}
