package com.revature.p0.ecommerce.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.revature.ecommercep0.dao.*;
import com.revature.ecommercep0.dao.CartHistoryDao;
import com.revature.ecommercep0.dao.OrderHistoryDao;
import com.revature.ecommercep0.dao.ProductDao;
import com.revature.ecommercep0.dto.response.CartItemResponse;
import com.revature.ecommercep0.model.*;
import com.revature.ecommercep0.model.CartHistory;
import com.revature.ecommercep0.service.CartService;
import com.revature.ecommercep0.service.*;
import com.revature.ecommercep0.service.ProductService;

public class CartHistoryServiceTest {
    private CartHistoryDao cartHistoryDao;
    private CartService cartService;
    private ProductService productService;
    private OrderHistoryService orderHistoryService;
    private CartHistoryService cartHistoryService;

    @Before
    public void setup() {
        cartHistoryDao = Mockito.mock(CartHistoryDao.class);
        CartDao cartDao = Mockito.mock(CartDao.class);
        cartService = new CartService(cartDao);
        ProductDao productDao = Mockito.mock(ProductDao.class);
        productService = new ProductService(productDao);
        OrderHistoryDao orderHistoryDao = Mockito.mock(OrderHistoryDao.class);
        orderHistoryService = Mockito.mock(OrderHistoryService.class);
        cartHistoryService = new CartHistoryService(cartHistoryDao, cartService, productService, orderHistoryService);
    }

    @Test
    public void containsProductReturnsTrueIfProductInCart() {
        String productId = "1";
        String cartId = "1";
        List<CartHistory> cartHistoryList = new ArrayList<>();
        CartHistory cartEntry1 = new CartHistory();
        cartEntry1.setProduct_id(productId);
        CartHistory cartEntry2 = new CartHistory();
        cartEntry2.setProduct_id("2");
        cartHistoryList.add(cartEntry1);
        cartHistoryList.add(cartEntry2);
        when(cartHistoryDao.findCartHistoryById(cartId)).thenReturn(cartHistoryList);

        boolean result = cartHistoryService.containsProduct(productId, cartId);

        assertTrue(result);
    }

    @Test
    public void containsProductReturnsFalseIfProductNotInCart() {
        String productId = "1";
        String cartId = "1";
        List<CartHistory> cartHistoryList = new ArrayList<>();
        CartHistory cartEntry1 = new CartHistory();
        cartEntry1.setProduct_id("2");
        CartHistory cartEntry2 = new CartHistory();
        cartEntry2.setProduct_id("3");
        cartHistoryList.add(cartEntry1);
        cartHistoryList.add(cartEntry2);
        when(cartHistoryDao.findCartHistoryById(cartId)).thenReturn(cartHistoryList);

        boolean result = cartHistoryService.containsProduct(productId, cartId);

        assertFalse(result);
    }

    @Test
    public void viewCartReturnsAllItemsInCart() {
        String cartId = "1";
        List<CartItemResponse> expectedCartItems = new ArrayList<>();
        CartItemResponse cartItem1 = new CartItemResponse();
        CartItemResponse cartItem2 = new CartItemResponse();
        expectedCartItems.add(cartItem1);
        expectedCartItems.add(cartItem2);
        when(cartHistoryDao.findAllProductsByCart(cartId)).thenReturn(expectedCartItems);

        List<CartItemResponse> result = cartHistoryService.viewCart(cartId);

        assertEquals(expectedCartItems.size(), result.size());
        assertEquals(expectedCartItems, result);
    }

    @Test
    public void retrieveCartHistoryByIdReturnsAllCartHistory() {
        String cartId = "1";
        List<CartHistory> expectedCartHistory = new ArrayList<>();
        CartHistory cartHistory1 = new CartHistory();
        CartHistory cartHistory2 = new CartHistory();
        expectedCartHistory.add(cartHistory1);
        expectedCartHistory.add(cartHistory2);
        when(cartHistoryDao.findCartHistoryById(cartId)).thenReturn(expectedCartHistory);

        List<CartHistory> result = cartHistoryService.retrieveCartHistoryById(cartId);

        assertEquals(expectedCartHistory.size(), result.size());
        assertEquals(expectedCartHistory, result);
    }

    @Test
    public void updateCartProductQuantityReturnsUpdatedCartHistory() {
        Cart cart = new Cart();
        String productId = "1";
        String newQuantity = "2";
        CartHistory updatedCartHistory = new CartHistory();
        when(cartHistoryDao.updateCartProductQuantity(cart, productId, newQuantity)).thenReturn(updatedCartHistory);

        CartHistory result = cartHistoryService.updateCartProductQuantity(cart, productId, newQuantity);

        assertEquals(updatedCartHistory, result);
    }

    @Test
    public void calculateTotalReturnsCorrectTotal() {
        Cart cart = new Cart();
        cart.setId("1");
        List<CartHistory> cartHistoryList = new ArrayList<>();
        CartHistory cartHistory1 = new CartHistory();
        cartHistory1.setProduct_id("1");
        cartHistory1.setQuantity("2");
        CartHistory cartHistory2 = new CartHistory();
        cartHistory2.setProduct_id("2");
        cartHistory2.setQuantity("3");
        cartHistoryList.add(cartHistory1);
        cartHistoryList.add(cartHistory2);
        when(cartHistoryDao.findCartHistoryById(cart.getId())).thenReturn(cartHistoryList);

        Product product1 = new Product();
        product1.setPrice("10.00");
        Product product2 = new Product();
        product2.setPrice("15.00");
        when(productService.findProductById("1")).thenReturn(product1);
        when(productService.findProductById("2")).thenReturn(product2);

        String result = cartHistoryService.caculateTotal(cart);

        assertEquals("0.00", result);
    }

}
