package com.revature.ecommercep0.service;

import java.util.List;

import com.revature.ecommercep0.dao.OrderDao;
import com.revature.ecommercep0.model.Order;

public class OrderService {
    OrderDao orderDao;

    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }
    public Order createNewOrder(Order newOrder) {
        return orderDao.save(newOrder);
    }
    public List<Order> findAll() {
        return orderDao.findAll();
    }
    
}
