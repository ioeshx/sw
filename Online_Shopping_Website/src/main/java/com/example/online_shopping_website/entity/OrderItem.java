package com.example.online_shopping_website.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 其实这个才应该是Order类
 * 现在的Order类应该是ParenOrder类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    private int orderId;
    private int goodsId;
    private String goodsName;
    private BigDecimal unitPrice;
    private int goodsNum;
    private BigDecimal itemPrice;
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
