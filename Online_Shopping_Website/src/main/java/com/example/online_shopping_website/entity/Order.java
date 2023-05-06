package com.example.online_shopping_website.entity;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private int orderId;
    private String username;
    private Date orderTime;
    private BigDecimal totalPrice;
    private int addressId;
    private int status;

    public Order(String username, Date orderTime, BigDecimal totalPrice, int addressId, int status) {
        this.username = username;
        this.orderTime = orderTime;
        this.totalPrice = totalPrice;
        this.addressId = addressId;
        this.status = status;
    }

    public Order(String username, BigDecimal totalPrice, int addressId, int status) {
        this.username = username;
        this.totalPrice = totalPrice;
        this.addressId = addressId;
        this.status = status;
    }
}
