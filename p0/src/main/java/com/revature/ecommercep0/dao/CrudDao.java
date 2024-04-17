package com.revature.ecommercep0.dao;

import java.util.List;

public interface CrudDao<T> {
    void save(T obj);
    void update(T obj);
    void delete(String id);
    List<T> findAll();
    T findById(String id);
}
