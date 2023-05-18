package com.example.online_shopping_website.mapper;

import com.example.online_shopping_website.entity.Order;
import com.example.online_shopping_website.entity.OrderItem;
import com.example.online_shopping_website.entity.ParentOrder;

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
    void SetOrderToPendingReception(int orderId);
    void SetOrderToDeleted(int orderId);
    void SetOrderToPendingRefund(int orderId);
    void SetOrderToRefunded(int orderId);
    List<Order> GetOrdersByShopName(String shopName);
    List<Order> GetOrdersByShopNameAndStatus(String shopName, int status);
    int GetMonthlySalesByShopName(String shopName);
    ParentOrder CreateParentOrder();
    void UpdateParentOrder(int parentOrderId ,BigDecimal totalPrice,  BigDecimal actualPayment, BigDecimal reducedPayment, int isPromotionEffective);

    BigDecimal GetActualPaymentBySubOrderId(int orderId);
    boolean IsOrderInPromotion(int orderId);
    int GetParentOrderId(int orderId);
    ParentOrder GetParentOrderByParentOrderId(int parentOrderId);
}
