package com.javarush.kojin.repository;

import com.javarush.kojin.entity.Role;
import com.javarush.kojin.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
    UserRepository userRepository;
    User user;
    User admin;
    @BeforeEach
    void init(){
        userRepository = new UserRepository();
        admin = User.builder()
                .login("admin")
                .password("admin")
                .name("Admin")
                .role(Role.ADMIN)
                .userQuests(new ArrayList<>())
                .userGames(new ArrayList<>())
                .build();
        user = User.builder()
                .login("user")
                .password("user")
                .name("User")
                .role(Role.USER)
                .userQuests(new ArrayList<>())
                .userGames(new ArrayList<>())
                .build();
        userRepository.create(admin);
        userRepository.create(user);
    }

    @Test
    void whenUserFindByLoginThenReturnsUserFromRepo() {
        User pattern = User.builder()
                .login("user")
                .build();

        Optional<User> testObject = userRepository.find(pattern).findFirst();
        assertTrue(testObject.isPresent());
        String name = user.getName();
        String testName = testObject.get().getName();
        assertEquals(name, testName);
    }

    @Test
    void whenCreateUserThenUserFromRepoHasId(){
        User testUser = User.builder()
                        .login("newUser")
                                .build();
        userRepository.create(testUser);
        assertNotNull(testUser.getId());
    }

    @Test
    void whenGetUserFromRepoThenReturnsCorrectUser(){
        Long userId = user.getId();

        User testUser = userRepository.get(userId);
        assertEquals(user, testUser);
    }

    @Test
    void whenGetAllUsersThenTakenUsersAreMatches(){
        Collection<User> allUsers = userRepository.getAll();
        assertTrue(allUsers.contains(admin));
        assertTrue(allUsers.contains(user));
    }

    @Test
    void whenUserUpdatedWhenTakenActualUser(){
        user.setRole(Role.ADMIN);
        userRepository.update(user);
        User testUser = userRepository.get(user.getId());
        assertEquals(user.getRole(), testUser.getRole());
    }

    @Test
    void whenDeleteUserThenTakenNull() {
        Long userId = user.getId();
        userRepository.delete(user.getId());

        User testUser = userRepository.get(userId);
        assertNull(testUser);
    }
}