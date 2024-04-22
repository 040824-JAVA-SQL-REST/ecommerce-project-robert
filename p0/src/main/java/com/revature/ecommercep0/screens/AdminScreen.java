package com.revature.ecommercep0.screens;

import java.util.List;
import java.util.Scanner;

import com.revature.ecommercep0.model.Product;
import com.revature.ecommercep0.model.User;
import com.revature.ecommercep0.service.ProductService;
import com.revature.ecommercep0.service.RouterService;

public class AdminScreen extends BaseScreen {
    private final Scanner scan;
    private User session;
    private final ProductService productService;
    private final RouterService routerService;

    public AdminScreen(Scanner scan, User session, ProductService productService, RouterService routerService) {
        this.scan = scan;
        this.session = session;
        this.productService = productService;
        this.routerService = routerService;
    }

    @Override
    public void startInterface() {

        while (true) {
            clearScreen();
            System.out.println("Welcome Admin " + session.getFname());
            // productService.deleteProductFromCatalog("apples");
            //productService.retrieveDeletedProduct("apples");
           // productService.updateProductDescriptionByName("oranges", "Oranic pink oranges");
          // productService.updateProductColumnByName("price", "6.66", "oranges");
           // productService.updateProductNameByName("apples", "oranges");
         //   productService.enterNewProductIntoCatalog("Guanaba", "organic purple guanaba", "$5.00", "produce");
            printProductCatalog(productService.findAll());
            scan.nextLine();

        }
    }

    private void printProductCatalog(List<Product> products) {
        for (Product product : products) {
            if (product.isAvailable()) {
                System.out.println(product.getName() + " : " + product.getDescription() + " : " + product.getCategory() + " : " + product.getPrice());
            }

        }
    }

}
