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
        if (isFound == null) { // if cart isnt already in the db, we save it
            return cartDao.save(cart);
        }

        return null; // otherwise returns null, indicating failure
    }

    public Cart findCartByUserID(String user_id) {
        return cartDao.findByUserId(user_id);
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
    // public static double getTotalProductPrice(String product_id, String quantity)
    // {
    // return CartService.convertCostStrToInt(quantity) *
    // CartService.convertCostStrToInt(productService.findProductById(product_id).getPrice())

    // }
}
