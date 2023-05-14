package com.example.online_shopping_website.entity.constant;

public class TransactionType {
    public static final int chargeToPrivateAccount = 1;     //个人账户充值
    public static final int chargeToShopAccount = 2;        //商店账户充值
    public static final int chargeToIntermediaryAccount = 3;    //中间账户充值
    public static final int chargeToProfitAccount = 4;          //利润账户充值
    public static final int registerCapitalToIntermediaryAccount = 5;   //申请商店注册，商店注册资金向中间账户转账
    public static final int registerCapitalToProfitAccount = 6;         //管理员同意商店注册，商店注册资金向利润账户转账
    public static final int shopAccountToPrivateAccount = 7;            //删除商店后，商店账户转入个人账户
}
