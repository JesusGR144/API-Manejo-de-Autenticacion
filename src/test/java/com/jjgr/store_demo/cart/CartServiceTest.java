package com.jjgr.store_demo.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jjgr.store_demo.exceptions.ApiRequestException;
import com.jjgr.store_demo.user.User;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {
    @Mock
    private CartRepository cartRepository;
    private CartService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CartService(cartRepository);
    }

    @Test
    void getCarts_shouldThrowExceptionWhenNoCartsFound() {
        // Dado
        when(cartRepository.findAll()).thenReturn(List.of());

        // Cuando & Entonces
        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
            underTest.getCarts();
        });
        assertEquals("No carts found", exception.getMessage());
    }

    @Test
    void getCarts_shouldReturnListOfCartsWhenFound() {
        // Dado
        User user1 = new User("User1", "user1@mail.com");
        Cart cart1 = new Cart(user1);
        Cart cart2 = new Cart(user1);
        List<Cart> carts = List.of(cart1, cart2);
        when(cartRepository.findAll()).thenReturn(carts);

        // Cuando
        List<Cart> result = underTest.getCarts();

        // Entonces
        verify(cartRepository).findAll();
        assertEquals(carts, result);
    }

    @Test
    void addCart_shouldThrowExceptionWhenCartIdIsTaken() {
        // Dado
        User user = new User("User1", "user1@mail.com");
        Cart cart = new Cart(user);
        when(cartRepository.findById(cart.getId())).thenReturn(Optional.of(cart));

        // Cuando & Entonces
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            underTest.addCart(cart);
        });
        assertEquals("Id is already taken", exception.getMessage());
    }

    @Test
    void addCart_shouldSaveCartWhenIdIsNotTaken() {
        // Dado
        User user = new User("User1", "user1@mail.com");
        Cart cart = new Cart(user);
        when(cartRepository.findById(cart.getId())).thenReturn(Optional.empty());

        // Cuando
        underTest.addCart(cart);

        // Entonces
        ArgumentCaptor<Cart> cartArgumentCaptor = ArgumentCaptor.forClass(Cart.class);
        verify(cartRepository).save(cartArgumentCaptor.capture());

        Cart capturedCart = cartArgumentCaptor.getValue();
        assertEquals(cart, capturedCart);
    }

    @Test
    void deleteCart_shouldThrowExceptionWhenCartDoesNotExist() {
        // Dado
        Long cartId = 1L;
        when(cartRepository.existsById(cartId)).thenReturn(false);

        // Cuando & Entonces
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            underTest.deleteCart(cartId);
        });
        assertEquals("Cart with id " + cartId + " does not exist", exception.getMessage());
        verify(cartRepository, never()).deleteById(cartId);
    }

    @Test
    void deleteCart_shouldDeleteCartWhenExists() {
        // Dado
        Long cartId = 1L;
        when(cartRepository.existsById(cartId)).thenReturn(true);

        // Cuando
        underTest.deleteCart(cartId);

        // Entonces
        verify(cartRepository).deleteById(cartId);
    }
}
