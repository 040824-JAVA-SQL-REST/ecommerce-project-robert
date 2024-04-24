package com.revature.ecommercep0.service;

import java.util.List;

import com.revature.ecommercep0.dao.CartHistoryDao;
import com.revature.ecommercep0.model.Cart;
import com.revature.ecommercep0.model.CartHistory;

public class CartHistoryService {
    private CartHistoryDao cartHistoryDao;
    private CartService cartService;
    private ProductService productService;

    public CartHistoryService(CartHistoryDao cartHistoryDao, CartService cartService, ProductService productService) {
        this.cartHistoryDao = cartHistoryDao;
        this.cartService = cartService;
        this.productService = productService;
    }
    public List<CartHistory> retrieveCartHistoryById(String cart_id) {
        List<CartHistory> cartHistory = cartHistoryDao.findCartHistoryById(cart_id);
        return cartHistory;
        //cartid, productid, and quantity
        
    }
    public CartHistory addToCart(String cart_id, String product_id, String quantity) {
        Cart myCart = cartService.findCartById(cart_id);
        if (myCart == null) return null;
        CartHistory cH = cartHistoryDao.save(new CartHistory(cart_id, product_id, quantity));
        double currPrice = CartService.convertCartTotalCostStrToInt(myCart);
        System.out.println("Current Cart total: " + currPrice);       
        double productTotal = productService.getTotalProductPrice(product_id, quantity);
        System.out.println("Total amount of products in cart: " + productTotal);
        double newPrice = currPrice + productTotal;
        System.out.println("New cart total: " + newPrice);
        String newPriceStr = String.format("%.2f", newPrice);
        System.out.println("New cart total: " + newPriceStr);
        System.out.println("New supposed price: " + cartService.updateCartPrice(myCart, newPriceStr).getTotal_cost());
        return cH;
    }
    
}
