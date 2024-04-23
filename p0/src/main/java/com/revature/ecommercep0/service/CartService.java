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
        Cart isFound = cartDao.findByUserId(cart.getUser_id());
        if (isFound == null) { //if cart isnt already in the db, we save it 
            return cartDao.save(cart);
        }

        return null; //otherwise returns null, indicating failure
    }
    public Cart findCartByUserID(String user_id) {
        return cartDao.findById(user_id);
    }   
    public Cart updateCartPrice(Cart cart, String newPrice) {
     //   double currentPrice = Double.parseDouble(cart.getTotal_cost());
        cart.setTotal_cost(newPrice);
        cartDao.updateCartPriceById(newPrice, cart.getId());
        return cart;
    }

    public double convertCartTotalCostStrToInt(Cart cart) {
        return Double.parseDouble(cart.getTotal_cost());
    }
}
