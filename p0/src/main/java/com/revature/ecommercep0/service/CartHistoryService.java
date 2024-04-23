package com.revature.ecommercep0.service;

import java.util.List;

import com.revature.ecommercep0.dao.CartHistoryDao;
import com.revature.ecommercep0.model.CartHistory;

public class CartHistoryService {
    private CartHistoryDao cartHistoryDao;

    public CartHistoryService(CartHistoryDao cartHistoryDao) {
        this.cartHistoryDao = cartHistoryDao;
    }
    public List<CartHistory> retrieveCartHistoryById(String cart_id) {
        List<CartHistory> cartHistory = cartHistoryDao.findCartHistoryById(cart_id);
        return cartHistory;
        //cartid, productid, and quantity
        
    }
    
}
