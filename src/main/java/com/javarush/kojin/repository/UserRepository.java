package com.javarush.kojin.repository;

import com.javarush.kojin.entity.User;

import java.util.stream.Stream;

public class UserRepository extends BaseRepository<User> {
    @Override
    public Stream<User> find(User pattern) {
        return map.values()
                .stream()
                .filter(u -> nullOrEquals(pattern.getId(), u.getId()))
                .filter(u -> nullOrEquals(pattern.getName(), u.getName()))
                .filter(u -> nullOrEquals(pattern.getLogin(), u.getLogin()))
                .filter(u -> nullOrEquals(pattern.getPassword(), u.getPassword()))
                .filter(u -> nullOrEquals(pattern.getRole(), u.getRole()))
                .filter(u -> nullOrEquals(pattern.getImageId(), u.getImageId()));
    }
}
