package com.javarush.kojin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
}
