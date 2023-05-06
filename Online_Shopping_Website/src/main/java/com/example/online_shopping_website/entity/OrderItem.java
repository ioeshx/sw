package com.example.online_shopping_website.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    private int orderId;
    private int goodsId;
    private String goodsName;
    private BigDecimal unitPrice;
    private int goodsNum;
    private int shopId;
    private String shopName;

    public OrderItem(int orderId, int goodsId, String goodsName, BigDecimal unitPrice, int goodsNum, String shopName) {
        this.orderId = orderId;
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.unitPrice = unitPrice;
        this.goodsNum = goodsNum;
        this.shopName = shopName;
    }
}
