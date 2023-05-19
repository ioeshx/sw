package com.example.online_shopping_website.controller;


import com.example.online_shopping_website.service.IOrderService;
import com.example.online_shopping_website.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class OrderController {
    @Autowired
    private IOrderService orderService;

    @RequestMapping("/api/orderCreating")
    public JsonResult orderCreating(@RequestBody Map<String,Object> map){
        List<Integer> allGoodsId = (List<Integer>)map.get("goodsIdList");
        List<Integer> allGoodsNum = (List<Integer>)map.get("numList");
        String username = (String)map.get("username");
        int addressId = (int)map.get("addressId");
        return orderService.orderCreating(username, addressId, allGoodsId, allGoodsNum);
    }

    @RequestMapping("/api/getOrderByOrderId")
    public JsonResult getOrderByOrderId(@RequestBody Map<String,Object> map){
        int orderId = (int)map.get("OrderId");
        return orderService.getOrderByOrderId(orderId);
    }

    @RequestMapping ("/api/getAllOrders")
    public JsonResult getAllOrders(@RequestBody Map<String,Object> map){
        String username = (String)map.get("username");
        return orderService.getAllOrders(username);

    }

    @RequestMapping("/api/payment")
    public JsonResult payment(@RequestBody Map<String,Object> map){
        List<Integer> orderIdList = (List<Integer>) map.get("orderIdList");
        return orderService.payment(orderIdList);
    }

    @RequestMapping("/api/cancelOrder")
    public JsonResult cancelOrder(@RequestBody Map<String,Object> map){
        List<Integer> orderIdList = (List<Integer>) map.get("orderIdList");
        return orderService.cancelOrder(orderIdList);
    }

    @RequestMapping("/api/confirmReceipt")
    public JsonResult confirmReceipt(@RequestBody Map<String,Object> map){
        List<Integer> orderIdList = (List<Integer>) map.get("orderIdList");
        return orderService.confirmReceipt(orderIdList);
    }

    @RequestMapping("/api/makingDelivery")
    public JsonResult makingDelivery(@RequestBody Map<String,Object> map){
        List<Integer> orderIdList = (List<Integer>) map.get("orderIdList");
        return orderService.makingDelivery(orderIdList);
    }

    @RequestMapping("/api/getOrdersByStatus")
    public JsonResult getOrdersByStatus(@RequestBody Map<String,Object> map){
        String username = (String)map.get("username");
        int status = (int)map.get("status");
        return orderService.getOrdersByStatus(username, status);
    }

    @RequestMapping("/api/deleteOrder")
    public JsonResult deleteOrder(@RequestBody Map<String,Object> map){
        List<Integer> orderIdList = (List<Integer>) map.get("orderIdList");
        return orderService.deleteOrder(orderIdList);
    }

    @RequestMapping("/api/refundOrder")
    public JsonResult refundOrder(@RequestBody Map<String,Object> map){
        List<Integer> orderIdList = (List<Integer>) map.get("orderIdList");
        return orderService.refundOrder(orderIdList);
    }

    @RequestMapping("/api/agreeToRefund")
    public JsonResult agreeToRefund(@RequestBody Map<String,Object> map) {
        List<Integer> orderIdList = (List<Integer>) map.get("orderIdList");
        return orderService.agreeToRefund(orderIdList);
    }

    @RequestMapping("/api/getAllOrdersOfShop")
    public JsonResult getAllOrdersOfShop(@RequestBody Map<String,Object> map){
        String shopName = (String)map.get("shopName");
        return orderService.getAllOrdersOfShop(shopName);
    }

    @RequestMapping("/api/getOrdersOfShopByStatus")
    public JsonResult getOrdersOfShopByStatus(@RequestBody Map<String,Object> map){
        String shopName = (String)map.get("shopName");
        int status = (int)map.get("status");
        return orderService.getOrdersOfShopByStatus(shopName, status);
    }


}
