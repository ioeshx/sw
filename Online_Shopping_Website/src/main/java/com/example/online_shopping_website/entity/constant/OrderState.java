package com.example.online_shopping_website.entity.constant;

public class OrderState {
    public static final int deleted = -1;           //删除订单
    public static final int pendingPayment = 0;     //待支付
    public static final int pendingDelivery = 1;    //待发货
    public static final int pendingReception = 2;   //待收货
    public static final int pendingRefunding = 3;   //待退款
    public static final int finished = 4;           //订单完成
    public static final int canceled = 5;           //订单取消
    public static final int refunded = 6;           //已退款
    public static final int applyingForRefund = 7;     //申请退款中

}
