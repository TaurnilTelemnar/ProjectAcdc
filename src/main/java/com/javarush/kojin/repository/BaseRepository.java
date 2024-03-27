package com.javarush.kojin.repository;

import com.javarush.kojin.entity.AbstractEntity;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public abstract class BaseRepository<T extends AbstractEntity> implements Repository<T>{

    protected final Map<Long, T> map = new ConcurrentHashMap<>();
    protected final AtomicLong id = new AtomicLong(0L);

    @Override
    public void create(T entity) {
        entity.setId(id.incrementAndGet());
        update(entity);
    }

    @Override
    public T get(Long id) {
        return map.get(id);
    }

    @Override
    public Collection<T> getAll() {
        return map.values();
    }

    @Override
    public void update(T entity) {
        map.put(entity.getId(), entity);
    }

    @Override
    public void delete(Long id) {
        map.remove(id);
    }

    protected boolean nullOrEquals(Object pattern, Object repo){
        return pattern == null || pattern.equals(repo);
    }
}
