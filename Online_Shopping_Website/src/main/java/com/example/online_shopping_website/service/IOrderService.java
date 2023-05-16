package com.example.online_shopping_website.service;

import com.example.online_shopping_website.util.JsonResult;

import java.util.List;

public interface IOrderService {

    JsonResult orderCreating(String username, int addressId, List<Integer> allGoodsId, List<Integer> allGoodsNum);
    JsonResult getOrderByOrderId(int orderId);
    JsonResult getAllOrders(String username);
    JsonResult payment(List<Integer> orderIdList);
    JsonResult cancelOrder(List<Integer> orderIdList);
    JsonResult confirmReceipt(List<Integer> orderIdList);
    JsonResult makingDelivery(List<Integer> orderIdList);
    JsonResult getOrdersByStatus(String username, int status);
    JsonResult deleteOrder(List<Integer> orderIdList);
    JsonResult refundOrder(List<Integer> orderIdList);
    JsonResult agreeToRefund(List<Integer> orderIdList);
    JsonResult getAllOrdersOfShop(String shopName);
    JsonResult getOrdersOfShopByStatus(String shopName, int status);
}
