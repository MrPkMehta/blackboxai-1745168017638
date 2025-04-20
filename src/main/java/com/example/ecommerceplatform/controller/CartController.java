package com.example.ecommerceplatform.controller;

import com.example.ecommerceplatform.model.Cart;
import com.example.ecommerceplatform.model.User;
import com.example.ecommerceplatform.service.CartService;
import com.example.ecommerceplatform.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Cart> getCart(Authentication authentication) {
        User user = userService.findByUsername(authentication.getName()).orElseThrow();
        Cart cart = cartService.getCartByUser(user);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> addItemToCart(Authentication authentication,
                                              @RequestParam Long productId,
                                              @RequestParam int quantity) {
        User user = userService.findByUsername(authentication.getName()).orElseThrow();
        Cart cart = cartService.addItemToCart(user, productId, quantity);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/remove")
    public ResponseEntity<Cart> removeItemFromCart(Authentication authentication,
                                                   @RequestParam Long productId) {
        User user = userService.findByUsername(authentication.getName()).orElseThrow();
        Cart cart = cartService.removeItemFromCart(user, productId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/clear")
    public ResponseEntity<Cart> clearCart(Authentication authentication) {
        User user = userService.findByUsername(authentication.getName()).orElseThrow();
        Cart cart = cartService.clearCart(user);
        return ResponseEntity.ok(cart);
    }
}
