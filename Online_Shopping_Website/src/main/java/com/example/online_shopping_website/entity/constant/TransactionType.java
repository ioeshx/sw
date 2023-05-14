package com.example.online_shopping_website.entity.constant;

public class TransactionType {
    public static final int chargeToPrivateAccount = 1;         //个人账户充值
    public static final int chargeToShopAccount = 2;            //商店账户充值
    public static final int chargeToIntermediaryAccount = 3;    //中间账户充值
    public static final int chargeToProfitAccount = 4;          //利润账户充值
    public static final int registerCapitalToIntermediaryAccount = 5;   //申请商店注册，商店注册资金向中间账户转账
    public static final int registerCapitalToProfitAccount = 6;         //管理员同意商店注册，商店注册资金从中间账户向利润账户转账
    public static final int registerCapitalToPrivateAccount = 12;        //管理员拒绝商店注册，商店注册资金从中间账户向个人账户转账
    public static final int shopAccountToPrivateAccount = 7;            //删除商店后，商店账户转入个人账户
    public static final int paymentToIntermediaryAccount = 8;           //支付订单后，金额转入中间账户
    public static final int commissionToProfitAccount = 9;              //完成订单后，佣金从中间账户转入商城利润账户
    public static final int profitToShopAccount = 10;                   //完成订单后，订单利润从中间账户转入商店账户
    public static final int refundToPrivateAccount = 11;                //同意退款后，订单金额从中间账户转回个人账户

}
