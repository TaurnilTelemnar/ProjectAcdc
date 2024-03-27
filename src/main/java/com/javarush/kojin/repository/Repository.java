package com.javarush.kojin.repository;

import java.util.Collection;
import java.util.stream.Stream;

public interface Repository <T>{
    Stream<T> find(T pattern);
    void create(T entity);
    T get(Long id);
    Collection<T> getAll();
    void update(T entity);
    void delete(Long id);
}
