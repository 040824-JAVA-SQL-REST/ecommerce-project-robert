package com.revature.ecommercep0.service;

import java.util.List;

import com.revature.ecommercep0.dao.CartHistoryDao;
import com.revature.ecommercep0.dto.response.CartItemResponse;
import com.revature.ecommercep0.model.Cart;
import com.revature.ecommercep0.model.CartHistory;
import com.revature.ecommercep0.model.Order;
import com.revature.ecommercep0.model.OrderHistory;
import com.revature.ecommercep0.model.Product;

public class CartHistoryService {
    private CartHistoryDao cartHistoryDao;
    private CartService cartService;
    private ProductService productService;
    private OrderHistoryService orderHistoryService;

    public CartHistoryService(CartHistoryDao cartHistoryDao, CartService cartService, ProductService productService,
            OrderHistoryService orderHistoryService) {
        this.cartHistoryDao = cartHistoryDao;
        this.cartService = cartService;
        this.productService = productService;
        this.orderHistoryService = orderHistoryService;
    }

    public boolean containsProduct(String product_id, String cart_id) {
        for (CartHistory cartEntry : retrieveCartHistoryById(cart_id)) {
            System.out.println("Products in cart: " + cartEntry.getProduct_id());
            if (cartEntry.getProduct_id().equals(product_id)) {
                return true;
            }
        }
        return false;
    }
    public List<CartItemResponse> viewCart(String cart_id) {
       return cartHistoryDao.findAllProductsByCart(cart_id);
    }


    public List<CartHistory> retrieveCartHistoryById(String cart_id) {
        List<CartHistory> cartHistory = cartHistoryDao.findCartHistoryById(cart_id);
        return cartHistory;
        // cartid, productid, and quantity
    }

    public CartHistory updateCartProductQuantity(Cart cart, String product_id, String newQuantity) {
        // TOOD: Allow users to update the quantity of items in their cart or remove
        // items altogether.
        CartHistory ch = cartHistoryDao.updateCartProductQuantity(cart, product_id, newQuantity);
        if (ch != null) {
            caculateTotal(cart);
            return cartHistoryDao.updateCartProductQuantity(cart, product_id, newQuantity);
        }
        return null;  
    }

    public String caculateTotal(Cart cart) {
        // CartHistory(cartid, productid, quantity) --- Cart(ID, user_id, total_cost)
        // TODO: Implement logic to calculate the total cost of items in the cart
        double total = 0.0;
        List<CartHistory> productsInCart = retrieveCartHistoryById(cart.getId());
        for (int i = 0; i < productsInCart.size(); i++) {
            CartHistory currentCartEntry = productsInCart.get(i);
            Product currProduct = productService.findProductById(currentCartEntry.getProduct_id());
            if (currProduct.isAvailable()) {
                double productPrice = Double
                        .parseDouble(productService.findProductById(currentCartEntry.getProduct_id()).getPrice());
                System.out.println("Product Price: " + productPrice);
                double quantity = Double.parseDouble(currentCartEntry.getQuantity());
                System.out.println("Quantity: " + quantity);
                total += (productPrice * quantity);
                System.out.println("Current total: " + total);
            }

        }
        String totalCartPrice = String.format("%.2f", total);

         cartService.updateCartPrice(cart, totalCartPrice);
        return totalCartPrice;
    }

    public CartHistory addToCart(String cart_id, String product_id, String quantity) {
        Cart myCart = cartService.findCartById(cart_id);
        if (myCart == null)
            return null;
        CartHistory cH = cartHistoryDao.save(new CartHistory(cart_id, product_id, quantity));
        caculateTotal(myCart);
        return cH;
    }
    public Order checkoutCart(Cart cart) {
        List<CartHistory> allCartEntriesByUser = retrieveCartHistoryById(cart.getId());
        Order newOrder = orderHistoryService.createNewOrder(new Order(cart.getTotal_cost(),"PENDING" , cart.getUser_id()));
        for (CartHistory cH : allCartEntriesByUser) {
            OrderHistory newOH = new OrderHistory(newOrder.getId(), cH.getProduct_id(), cH.getQuantity());
            orderHistoryService.addProductToOrder(newOH);
        }
        cart.setIs_CheckedOut(true);
        cartService.deleteCart(cart);
        return newOrder;
    }
    public ProductService getProductService() {
        return this.productService;
    }

}
