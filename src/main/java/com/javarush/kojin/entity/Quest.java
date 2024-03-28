package com.javarush.kojin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Quest implements AbstractEntity{
    private Long id;
    private String name;
    private Long authorId;
    private Long startQuestionId;
    private Collection<Question> questions;
    private Boolean isDraft;
    private Long imageId;
}