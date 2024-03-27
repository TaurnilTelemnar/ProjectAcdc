package com.javarush.kojin.config;

import com.javarush.kojin.entity.Role;
import com.javarush.kojin.entity.User;
import com.javarush.kojin.service.UserService;

public class Config {
    private final UserService userService;

    public Config(UserService userService) {
        this.userService = userService;
    }

    public void addStartData(){
        User admin = User.builder()
                .login("admin")
                .password("admin")
                .name("Admin")
                .role(Role.ADMIN)
                .build();
        User user = User.builder()
                .login("user")
                .password("user")
                .name("User")
                .role(Role.USER)
                .build();
        userService.createUser(admin);
        userService.createUser(user);
    }

}
