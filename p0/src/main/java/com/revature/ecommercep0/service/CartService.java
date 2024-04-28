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
        System.out.println("Theres a cart already active!");
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

    public void viewCart(Cart cart) {
        //TODO: View Cart: Provide functionality for users to view the contents of their cart, 
        // including item details and total cost.
    }
 
    public void deleteCart(Cart cart) {
        cart.setIs_CheckedOut(true);
        cartDao.update(cart);
        
    }
    public void clearCart(Cart cart) {
        //TODO: Clear cart idk how to yet doe.
    }

    public Cart updateCartPrice(Cart cart, String newPrice) {
        // double currentPrice = Double.parseDouble(cart.getTotal_cost());
        System.out.println("Cart ID in cartService: " + cart.getId());
       // cart.setTotal_cost(newPrice);
       System.out.println("Price in CartService: " + newPrice);
        cartDao.updateCartPriceById(cart.getId(), newPrice);
        return findCartById(cart.getId());
    }

    public static double convertCartTotalCostStrToInt(Cart cart) {
        return Double.parseDouble(cart.getTotal_cost());
    }

    public static double convertCostStrToInt(String price) {
        return Double.parseDouble(price);
    }
}
