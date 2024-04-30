package com.revature.p0.ecommerce.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import com.revature.ecommercep0.dao.OrderDao;
import com.revature.ecommercep0.model.Order;
import com.revature.ecommercep0.service.OrderService;

public class OrderServiceTest {
    private OrderService orderService;
    private OrderDao orderDao;

    @Before
    public void setUp() {
        orderDao = mock(OrderDao.class);
        orderService = new OrderService(orderDao);
    }
    @Test
    public void findByIdReturnsOrderIfExists() {
        String orderId = "123";
        Order expectedOrder = new Order();
        when(orderDao.findById(orderId)).thenReturn(expectedOrder);
        Order actualOrder = orderService.findById(orderId);
        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    public void findByIdReturnsNullIfNotFound() {
        String orderId = "456";
        when(orderDao.findById(orderId)).thenReturn(null);
        Order actualOrder = orderService.findById(orderId);
        assertEquals(null, actualOrder);
    }

    @Test
    public void createNewOrderReturnsOrderIfSaved() {
        Order newOrder = new Order();
        orderService.createNewOrder(newOrder);
        verify(orderDao).save(newOrder);
    }

    @Test
    public void findAllReturnsAllIfNotEmpty() {
        List<Order> expectedOrders = new ArrayList<>();
        expectedOrders.add(new Order());
        expectedOrders.add(new Order());
        when(orderDao.findAll()).thenReturn(expectedOrders);

        List<Order> actualOrders = orderService.findAll();

        assertEquals(expectedOrders.size(), actualOrders.size());
        assertEquals(expectedOrders, actualOrders);
    }

    
}
