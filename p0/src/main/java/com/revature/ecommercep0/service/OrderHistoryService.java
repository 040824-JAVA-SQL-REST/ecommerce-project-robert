package com.revature.ecommercep0.service;

import java.util.List;

import com.revature.ecommercep0.dao.OrderHistoryDao;
import com.revature.ecommercep0.dto.response.UserOrderItemResponse;
import com.revature.ecommercep0.model.Order;
import com.revature.ecommercep0.model.OrderHistory;
import com.revature.ecommercep0.model.Product;

public class OrderHistoryService {
    private OrderService orderService;
    private ProductService productService;
    private OrderHistoryDao orderHistoryDao;

    public OrderHistoryService(OrderService orderService, ProductService productService,
            OrderHistoryDao orderHistoryDao) {
        this.orderService = orderService;
        this.productService = productService;
        this.orderHistoryDao = orderHistoryDao;
    }
    public Order createNewOrder(Order order) {
        return orderService.createNewOrder(order);
    }
    public OrderHistory addProductToOrder(OrderHistory orderHistory) {
        return orderHistoryDao.save(orderHistory);
    }

    public List<OrderHistory> allProductEntriesByOrder(String order_id) {
        List<OrderHistory> allProductEntries = orderHistoryDao.findAllOrdersById(order_id);
        return allProductEntries;
    }
    public List<UserOrderItemResponse> allOrdersByUser(String user_id) {
        List<UserOrderItemResponse> allProductEntries = orderHistoryDao.findAllOrdersByUser(user_id);
        return allProductEntries;
    }

    public String caculateTotal(Order order) {
        double total = 0.0;
        List<OrderHistory> productsInCart = allProductEntriesByOrder(order.getId());
        for (int i = 0; i < productsInCart.size(); i++) {
            OrderHistory currentOrderEntry = productsInCart.get(i);
            Product currProduct = productService.findProductById(currentOrderEntry.getProductId());
            double productPrice = Double
                    .parseDouble(productService.findProductById(currentOrderEntry.getProductId()).getPrice());
            System.out.println("Product Price: " + productPrice);
            double quantity = Double.parseDouble(currentOrderEntry.getQuantity());
            System.out.println("Quantity: " + quantity);
            total += (productPrice * quantity);
            System.out.println("Current total: " + total);

        }
        String totalCartPrice = String.format("%.2f", total);

        return totalCartPrice;
    }

}
