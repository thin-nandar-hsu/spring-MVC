package com.example.mvccrud.ds;

/*
id,
title,
price,
quantity
 */

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedList;
import java.util.Objects;

@Getter
@Setter
@ToString
public class CartItem {
    private int id;
    private String title;
    private double price;
    private int quantity;
    private boolean render;
    private LinkedList<Integer> quantityLinkedList=new LinkedList<>();

    @Override    // hashCode & equal --> for Set<CartItem> in Cart class
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return id == cartItem.id && Objects.equals(title, cartItem.title);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }


public CartItem(){

}

    public CartItem(int id, String title, double price, int quantity) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
    }
}
