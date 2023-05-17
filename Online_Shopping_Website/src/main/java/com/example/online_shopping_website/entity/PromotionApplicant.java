package com.example.online_shopping_website.entity;

public class PromotionApplicant {
    private int id;
    private String username;
    private String shopName;
    private int isGoodsCategory;    //商店上架的商品种类，是否符合标准;0 代表不符合，1 代表符合，下同
    private int isCapital;          //商店的注册资金，是否符合标准
    private int isMonthlySale;      //商店的月销量，是否符合标准
    private int isMonthlyIncome;    //商店的月销售额，是否符合标准
    private int status;             //申请状态 0：代表申请中， 1：代表申请通过 2：代表申请被拒绝
}
