package com.example.online_shopping_website.mapper;

import com.example.online_shopping_website.entity.Order;
import com.example.online_shopping_website.entity.OrderItem;

import java.math.BigDecimal;

public interface OrderMapper {
    int creatOrder(Order newOrder);
    int createOrderItem(OrderItem orderItem);
}
