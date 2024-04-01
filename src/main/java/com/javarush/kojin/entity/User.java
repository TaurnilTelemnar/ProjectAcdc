package com.javarush.kojin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements AbstractEntity{
    private Long id;
    private String name;
    private String login;
    private String password;
    private Role role;
    private Long imageId;
    private Collection<Quest> userQuests = new ArrayList<>();
    private Collection<Game> userGames = new ArrayList<>();
}
