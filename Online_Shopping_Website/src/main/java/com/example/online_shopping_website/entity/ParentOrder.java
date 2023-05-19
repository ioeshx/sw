package com.example.online_shopping_website.entity;

import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParentOrder {
    private int parentOrderId;
    private BigDecimal totalPrice;      //优惠前的订单总金额
    private BigDecimal actualPayment;   //优惠后的订单总金额
    private BigDecimal reducedPayment;  //减免的金额
    private int isPromotionEffective;   //该父订单是否含有优惠生效的子订单 0不含 1含有

    public void setParentOrderId(int parentOrderId) {
        this.parentOrderId = parentOrderId;
    }
}
