package com.revature.ecommercep0.screens;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.ecommercep0.model.Product;
import com.revature.ecommercep0.model.User;
import com.revature.ecommercep0.service.ProductService;

public class HomeScreen extends BaseScreen {
    private User session;
    private final Scanner scan;
    private ProductService productService;

    public HomeScreen(User session, Scanner scan, ProductService productService) {
        this.session = session;
        this.scan = scan;
        this.productService = productService;
    }

    @Override
    public void startInterface() {
        clearScreen();
        System.out.println("Welcome " + session.getFname() + "!");
        List<Product> products = productService.findAll();
        printProductCatalog(products);
        pause(scan);
    }

    private void printProductCatalog(List<Product> products) {
        for (Product product : products) {
            if (product.isAvailable()) {
                System.out.println(product.getName() + " : " + product.getDescription());
            }

        }
    }
}
