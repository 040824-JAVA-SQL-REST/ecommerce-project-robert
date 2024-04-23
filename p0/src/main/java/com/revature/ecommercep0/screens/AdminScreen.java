package com.revature.ecommercep0.screens;

import java.util.List;
import java.util.Scanner;

import com.revature.ecommercep0.model.Order;
import com.revature.ecommercep0.model.Product;
import com.revature.ecommercep0.model.User;
import com.revature.ecommercep0.service.CartHistoryService;
import com.revature.ecommercep0.service.CartService;
import com.revature.ecommercep0.service.OrderService;
import com.revature.ecommercep0.service.ProductService;
import com.revature.ecommercep0.service.RouterService;
import com.revature.ecommercep0.model.Cart;
import com.revature.ecommercep0.model.CartHistory;


public class AdminScreen extends BaseScreen {
    private final Scanner scan;
    private User session;
    private final ProductService productService;
    private final RouterService routerService;
    private final OrderService orderService;
    private final CartService cartService;
    private final CartHistoryService cartHistoryService;

    public AdminScreen(Scanner scan, User session, ProductService productService, RouterService routerService,
            OrderService orderService, CartService cartService, CartHistoryService cartHistoryService) {
        this.scan = scan;
        this.session = session;
        this.productService = productService;
        this.routerService = routerService;
        this.orderService = orderService;
        this.cartService = cartService;
        this.cartHistoryService = cartHistoryService;
    }

    @Override
    public void startInterface() {

        while (true) {
            clearScreen();
            System.out.println("Welcome Admin " + session.getFname());
            // Order newOrder = new Order( "66.66", "PENDING", "1");
            Cart myCart = new Cart(session.getId());
            cartService.createNewCart(myCart);
            cartService.
            printCartDetails(cartHistoryService.retrieveCartHistoryById("1bdb9291-5106-4fbc-bf5a-bb95c1e036b0"));
            


          //  printAllOrders(orderService.findAll());

            // orderService.createNewOrder(newOrder);
            // productService.deleteProductFromCatalog("apples");
            // productService.retrieveDeletedProduct("apples");
            // productService.updateProductDescriptionByName("oranges", "Oranic pink
            // oranges");
            // productService.updateProductColumnByName("price", "6.66", "oranges");
            // productService.updateProductNameByName("apples", "oranges");
            // productService.enterNewProductIntoCatalog("Guanaba", "organic purple
            // guanaba", "$5.00", "produce");

           // printProductCatalog(productService.findAll());
            scan.nextLine();

        }
    }
    private void printCartDetails(List<CartHistory> cartHistory) {
        for (CartHistory ch : cartHistory) {
            System.out.println("user: " + cartService.findCartById(ch.getCart_id()).getUser_id() + ", product: " + productService.findProductById(ch.getProduct_id()).getName() +", price: " + productService.findProductById(ch.getProduct_id()).getPrice() + ", quantity: " + ch.getQuantity());
        }
    }

    private void printAllOrders(List<Order> orders) {
        for (Order order : orders) {
            System.out.println("Order id: " + order.getId() + " - " + "placed by USER " + order.getUser_id()
                    + " - Price: " + order.getTotalPrice() + " : " + order.getStatus());
        }

    }

    private void printProductCatalog(List<Product> products) {
        for (Product product : products) {
            if (product.isAvailable()) {
                System.out.println(product.getName() + " : " + product.getDescription() + " : " + product.getCategory()
                        + " : " + product.getPrice());
            }

        }
    }

}
