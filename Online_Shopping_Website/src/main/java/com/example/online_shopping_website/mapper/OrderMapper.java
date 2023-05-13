package com.example.online_shopping_website.mapper;

import com.example.online_shopping_website.entity.Order;
import com.example.online_shopping_website.entity.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public interface OrderMapper {
    int creatOrder(Order newOrder);
    Order getOrderByOrderId(int orderId);
    List<Order> getAllOrdersByUsername(String username);
    String getUsernameByOrderId(Integer orderId);
    BigDecimal getActualPaymentByOrderId(int orderId);
    void SetOrderToPendingDeliveryByOrderId(int orderId);
    int GetOrderStatusByOrderId(int orderId);
    void CancelOrderByOrderId(int orderId);
    void SetOrderToFinishedByOrderId(int orderId);
    String GetShopNameByOrderId(int orderId);
    int GetGoodsIdByOrderId(int orderId);
    int GetGoodsNumByOrderId(int orderId);
    List<Order> GetOrdersByStatus(String username, int status);
}
