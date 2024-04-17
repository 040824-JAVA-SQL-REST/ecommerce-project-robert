package com.revature.ecommercep0.dao;

import java.util.ArrayList;
import java.util.List;

import com.revature.ecommercep0.model.User;

public class UserDao implements CrudDao<User> {

    @Override
    public User findById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public void save(User obj) {
   
    }

    @Override
    public void update(User obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public List<User> findAll() {
        List<User> allUsers = new ArrayList<>();
        
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }
    
}
