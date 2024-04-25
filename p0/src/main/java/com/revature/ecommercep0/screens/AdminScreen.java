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
            
            Cart myCart = cartService.getActiveCartFromUser(session.getId());
            
            System.out.println("This is my cart: " + myCart);
            System.out.println("User_id: " + session.getId());
            System.out.println("Total cart price: " + cartHistoryService.caculateTotal(myCart));
            if (myCart == null) {
                myCart = new Cart(session.getId());
                System.out.println(myCart.getId());
                cartService.createNewCart(myCart);
            }
            //System.out.println(myCart + "Cart ID: " + myCart.getId() + ", User_Id: " + myCart.getUser_id() + ", total cost: " + myCart.getTotal_cost() + ", is chekedout: " + myCart.isIs_CheckedOut());
            System.out.println("Cart id: " + myCart.getId());
           // cartHistoryService.addToCart(myCart.getId(), "41", "10"); 
         //   cartService.createNewCart(myCart);
         //   CartHistory newCartHistory = cartHistoryService.findCartHistoryById
            printCartDetails(cartHistoryService.retrieveCartHistoryById(myCart.getId()));
            


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
            System.out.println( productService.updateProductColumnById("category" , "livestock", productService.findProductById("2")));
            printProductCatalog(productService.findAll());
            scan.nextLine();

        }
    }
    private void printCartDetails(List<CartHistory> cartHistory) {
        for (CartHistory ch : cartHistory) {
            Cart cart = cartService.getActiveCartFromUser(ch.getCart_id());
            System.out.println("Order Details--"+"user: " + cart.getUser_id() + ", product: " + productService.findProductById(ch.getProduct_id()).getName() +", Total price: " + cart.getTotal_cost() + ", quantity: " + ch.getQuantity());
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
                System.out.println("product id: " + product.getId() + " : " +product.getName() + " : " + product.getDescription() + " : " + product.getCategory()
                        + " : " + product.getPrice());
            }

        }
    }

}
