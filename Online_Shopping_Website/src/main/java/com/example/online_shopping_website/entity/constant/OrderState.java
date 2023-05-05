package com.example.online_shopping_website.entity.constant;

public class OrderState {
    public static final int deleted = -1;
    public static final int pendingPayment = 0;
    public static final int pendingDelivery = 1;
    public static final int pendingReception = 2;
    public static final int finished = 3;
    public static final int canceled = 4;
    public static final int refunded = 5;

}
