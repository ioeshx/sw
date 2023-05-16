package com.example.online_shopping_website.entity;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Promotion {
    public static final int promotionOngoing = 1;
    public static final int promotionEnd = 0;
    private Integer promotionId;
    private Integer status;                     //活动状态  1：活动进行中 0：活动结束
    private String promotionName;               //促销活动名称
    private Date endTime;                       //活动结束时间
    private String goodsTypeInPromotion;  //参与活动的商品类型
    private BigDecimal promotionFund;           //促销资金
    private BigDecimal promotionFundUsed;       //已经使用的促销资金
    private BigDecimal promotionStartLine;      //订单满减线，满y减x的y
    private BigDecimal promotionPaymentReduce;  //到达满减线之后，减多少，满y减x的x
    private BigDecimal registrationCapitalLimit;//申请参加活动的商店，注册资金限制
    private BigDecimal monthlySaleIncomeLimit;  //申请参加活动的商店，月销售额限制
    private Integer monthlySalesLimit;          //申请参加活动的商店，月销量限制

    public Promotion(String promotionName, Date endTime, String goodsTypeInPromotion, BigDecimal promotionFund, BigDecimal promotionStartLine, BigDecimal promotionPaymentReduce, BigDecimal registrationCapitalLimit, BigDecimal monthlySaleIncomeLimit, Integer monthlySalesLimit) {
        this.promotionName = promotionName;
        this.endTime = endTime;
        this.goodsTypeInPromotion = goodsTypeInPromotion;
        this.promotionFund = promotionFund;
        this.promotionStartLine = promotionStartLine;
        this.promotionPaymentReduce = promotionPaymentReduce;
        this.registrationCapitalLimit = registrationCapitalLimit;
        this.monthlySaleIncomeLimit = monthlySaleIncomeLimit;
        this.monthlySalesLimit = monthlySalesLimit;
    }
}
