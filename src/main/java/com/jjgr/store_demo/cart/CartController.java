package com.jjgr.store_demo.cart;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/cart")
public class CartController {
    private final CartService cartService;

    //Constructor
    public CartController(CartService cartService){
        this.cartService = cartService;
    }

    // @GetMapping("getAll")
    // public List<Cart> getAll(){
    //     return cartService.getCarts();
    // }

    // @PostMapping("addCart")
    // public void addCart(@RequestBody Cart cart){
    //     cartService.addCart(cart);
    // }

    // @DeleteMapping(path = "{cartId}")
    // public void deleteCart(@PathVariable("cartId") Long cartId){
    //     cartService.deleteCart(cartId);
    // }
    @GetMapping("getAll")
    public List<Cart> getAllCarts(){
        return cartService.getCarts();
    }

    @PostMapping("addCart")
    public ResponseEntity<String> addCart(@RequestBody Cart cart){
        cartService.addCart(cart);
        return ResponseEntity.ok("Cart added successfuly");
    }

    @DeleteMapping("{cartId}")
    public ResponseEntity<String> deleteCart(@PathVariable Long cartId){
        cartService.deleteCart(cartId);
        return ResponseEntity.ok("Cart deleted successfully");
    }
}
