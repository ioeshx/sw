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
    private int addressId;
    private String shopName;
    private Date orderTime;
    private int goodsId;
    private String goodsName;
    private BigDecimal unitPrice;
    private int goodsNum;
    private BigDecimal totalPrice;
    private BigDecimal actualPayment;
    private int status;
    private int isPromotionEffective;
    private int parentOrderId;

    public Order(String username, int addressId, String shopName, Date orderTime, int goodsId, String goodsName, BigDecimal unitPrice, int goodsNum, BigDecimal totalPrice, BigDecimal actualPayment, int status, int isPromotionEffective, int parentOrderId) {
        this.username = username;
        this.addressId = addressId;
        this.shopName = shopName;
        this.orderTime = orderTime;
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.unitPrice = unitPrice;
        this.goodsNum = goodsNum;
        this.totalPrice = totalPrice;
        this.actualPayment = actualPayment;
        this.status = status;
        this.isPromotionEffective = isPromotionEffective;
        this.parentOrderId = parentOrderId;
    }
}
