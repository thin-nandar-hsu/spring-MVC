package com.example.mvccrud.ds;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashSet;
import java.util.Set;

@SessionScope
@Component
public class Cart {

    private Set<CartItem> cartItems= new HashSet<>();   // Set<> has *no duplicate*;

    public void addToCart(CartItem cartItem){
        this.cartItems.add(cartItem);
    }

    public void removeCart(CartItem cartItem){
        this.cartItems.remove(cartItem);
    }

    public void clearCart(){
        this.cartItems.clear();
    }

    public int cartSize(){
        return this.cartItems.size();
    }
}