package com.revature.ecommercep0.service;

import com.revature.ecommercep0.dao.CartDao;
import com.revature.ecommercep0.model.Cart;

public class CartService {
    private CartDao cartDao;

    public CartService(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public Cart findCartById(String cart_id) {
        return cartDao.findById(cart_id);
    }
    public Cart createNewCart(Cart cart) {
        //have to check for list<CarT> per user to make sure if theres a cart thats not checked-out
        Cart isFound = getActiveCartFromUser(cart.getUser_id());
        if (isFound == null) { // if cart isnt already in the db, we save it
            return cartDao.save(cart);
        }
        return null; // otherwise returns null, indicating failure
    }

    public Cart getActiveCartFromUser(String user_id) {
        Cart activeCart = null;
        for (Cart c: cartDao.findAllCartsByUserId(user_id)) {
            if (!c.isIs_CheckedOut()) {
                return c;
            }
        }
        return activeCart;
    }

    public void deleteCart(Cart cart) {
        cart.setIs_CheckedOut(true);
        cartDao.update(cart);
    }
    
    public Cart updateCartPrice(Cart cart, String newPrice) {
       // cart.setTotal_cost(newPrice);
        cartDao.updateCartPriceById(cart.getId(), newPrice);
        return findCartById(cart.getId());
    }
}
