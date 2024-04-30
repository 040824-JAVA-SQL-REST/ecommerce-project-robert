package com.revature.p0.ecommerce.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import com.revature.ecommercep0.dao.OrderDao;
import com.revature.ecommercep0.dao.OrderHistoryDao;
import com.revature.ecommercep0.dao.ProductDao;
import com.revature.ecommercep0.dto.response.AdminOrderItemResponse;
import com.revature.ecommercep0.dto.response.UserOrderItemResponse;
import com.revature.ecommercep0.model.*;
import com.revature.ecommercep0.model.OrderHistory;
import com.revature.ecommercep0.service.OrderHistoryService;
import com.revature.ecommercep0.service.OrderService;
import com.revature.ecommercep0.service.ProductService;

public class OrderHistoryServiceTest {
    private OrderHistoryService orderHistoryService;
    private ProductService productService;
    private OrderService orderService;
    private OrderHistoryDao orderHistoryDao;
    
    @Before
    public void setUp() {
        orderHistoryDao = mock(OrderHistoryDao.class);
        OrderDao orderDao = mock(OrderDao.class);
        ProductDao productDao = mock(ProductDao.class);
        productService = new ProductService(productDao);
        orderService = new OrderService(orderDao);
        orderHistoryService = new OrderHistoryService(orderService, productService, orderHistoryDao);
    }
    @Test
    public void createNewOrderReturnsOrderIfSaved() {
        Order newOrder = new Order();
        when(orderService.createNewOrder(newOrder)).thenReturn(newOrder);
        Order result = orderHistoryService.createNewOrder(newOrder);
        assertEquals(newOrder, result);
    }

    @Test
    public void getAllOrdersReturnsAllOrdersForAdmin() {
        List<AdminOrderItemResponse> expectedOrders = new ArrayList<>();
        AdminOrderItemResponse order1 = new AdminOrderItemResponse();
        order1.setOrder_id("1");
        AdminOrderItemResponse order2 = new AdminOrderItemResponse();
        order2.setOrder_id("2");
        expectedOrders.add(order1);
        expectedOrders.add(order2);
        when(orderHistoryDao.findAllOrdersForAdmin()).thenReturn(expectedOrders);

        List<AdminOrderItemResponse> result = orderHistoryService.getAllOrders();

        assertEquals(expectedOrders.size(), result.size());
        assertEquals(expectedOrders.get(0).getOrder_id(), result.get(0).getOrder_id());
        assertEquals(expectedOrders.get(1).getOrder_id(), result.get(1).getOrder_id());
    }
    @Test
    public void addProductToOrderReturnsOrderHistoryIfSaved() {
        OrderHistory orderHistory = new OrderHistory();
        when(orderHistoryDao.save(orderHistory)).thenReturn(orderHistory);
        OrderHistory result = orderHistoryService.addProductToOrder(orderHistory);
        assertEquals(orderHistory, result);
    }
    @Test
    public void allProductEntriesByOrderReturnsAllEntries() {
        String orderId = "1";
        List<OrderHistory> expectedEntries = new ArrayList<>();
        OrderHistory entry1 = new OrderHistory();
        OrderHistory entry2 = new OrderHistory();
        expectedEntries.add(entry1);
        expectedEntries.add(entry2);
        when(orderHistoryDao.findAllOrdersById(orderId)).thenReturn(expectedEntries);

        List<OrderHistory> result = orderHistoryService.allProductEntriesByOrder(orderId);

        assertEquals(expectedEntries.size(), result.size());
        assertEquals(expectedEntries, result);
    }
    @Test
    public void allOrdersByUserReturnsAllOrders() {
        String userId = "1";
        List<UserOrderItemResponse> expectedOrders = new ArrayList<>();
        UserOrderItemResponse order1 = new UserOrderItemResponse();
        UserOrderItemResponse order2 = new UserOrderItemResponse();
        expectedOrders.add(order1);
        expectedOrders.add(order2);
        when(orderHistoryDao.findAllOrdersByUser(userId)).thenReturn(expectedOrders);

        List<UserOrderItemResponse> result = orderHistoryService.allOrdersByUser(userId);

        assertEquals(expectedOrders.size(), result.size());
        assertEquals(expectedOrders, result);
    }

    @Test
    public void calculateTotalReturnsCorrectTotal() {
        String orderId = "1";
        Order order = new Order();
        order.setId(orderId);
        List<OrderHistory> orderHistoryList = new ArrayList<>();
        OrderHistory orderHistory1 = new OrderHistory();
        orderHistory1.setProductId("1");
        orderHistory1.setQuantity("2");
        OrderHistory orderHistory2 = new OrderHistory();
        orderHistory2.setProductId("2");
        orderHistory2.setQuantity("3");
        orderHistoryList.add(orderHistory1);
        orderHistoryList.add(orderHistory2);
        when(orderHistoryDao.findAllOrdersById(orderId)).thenReturn(orderHistoryList);

        Product product1 = new Product();
        product1.setPrice("10.00");
        Product product2 = new Product();
        product2.setPrice("15.00");
        when(productService.findProductById("1")).thenReturn(product1);
        when(productService.findProductById("2")).thenReturn(product2);

        String result = orderHistoryService.caculateTotal(order);

        assertEquals("65.00", result);
    }
    
}
