package com.javarush.kojin.service;

import com.javarush.kojin.entity.User;
import com.javarush.kojin.repository.UserRepository;

import java.util.Collection;
import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user){
        userRepository.create(user);
    }

    public Optional<User> getUser(Long id){
        User pattern = User.builder().id(id).build();
        return userRepository.find(pattern).findFirst();
    }

    public Optional<User> getUser(User pattern){
        return userRepository.find(pattern).findFirst();
    }

    public Collection<User> getAllUsers(){
        return userRepository.getAll();
    }
    public void updateUser(User user){
        userRepository.update(user);
    }
    public void deleteUser(Long id){
        userRepository.delete(id);
    }
}
