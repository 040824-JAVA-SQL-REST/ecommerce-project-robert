package com.revature.p0.ecommerce.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import com.revature.ecommercep0.dao.CartDao;
import com.revature.ecommercep0.model.Cart;
import com.revature.ecommercep0.service.CartService;

public class CartServiceTest {
    private CartDao cartDao;
    private CartService cartService;

    @Before
    public void setUp() {
        cartDao = mock(CartDao.class);
        cartService = new CartService(cartDao);
    }

    @Test
    public void findCartByIdReturnCartIfIdIsActive() {
        String cartId = "123";
        Cart expectedCart = new Cart();
        when(cartDao.findById(cartId)).thenReturn(expectedCart);

        Cart actualCart = cartService.findCartById(cartId);

        assertEquals(expectedCart, actualCart);
    }

    @Test
    public void createNewCartShouldReturnCartIfNewCartIsCreated() {
        Cart cart = new Cart();

        cart.setUser_id("user123");
        List<Cart> carts = List.of();
        when(cartDao.findAllCartsByUserId(cart.getUser_id())).thenReturn(carts);
        when(cartDao.save(cart)).thenReturn(cart);

        Cart result = cartService.createNewCart(cart);

        assertEquals(cart, result);
    }

    @Test
    public void getActiveCartFromUserShouldReturnCartIfFound() {
        String userId = "user123";
        Cart activeCart = new Cart();
        activeCart.setUser_id(userId);
        activeCart.setIs_CheckedOut(false);
        List<Cart> carts = new ArrayList<>();
        carts.add(activeCart);

        when(cartDao.findAllCartsByUserId(userId)).thenReturn(carts);
        Cart result = cartService.getActiveCartFromUser(userId);
        assertEquals(activeCart, result);
    }

    @Test
    public void deleteCart() {
        Cart cart = new Cart();
        cart.setIs_CheckedOut(false);

        cartService.deleteCart(cart);

        assertTrue(cart.isIs_CheckedOut());
        verify(cartDao, times(1)).update(cart);
    }
    
}
