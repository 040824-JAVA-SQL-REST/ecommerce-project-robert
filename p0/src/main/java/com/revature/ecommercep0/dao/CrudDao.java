package com.revature.ecommercep0.dao;

import java.util.List;

public interface CrudDao<T> {
    T save(T obj);
    T update(T obj);
    T delete(String id);
    List<T> findAll();
    T findById(String id);
}
