package com.example.online_shopping_website.service;

import com.example.online_shopping_website.entity.Good;
import com.example.online_shopping_website.entity.Promotion;
import com.example.online_shopping_website.mapper.GoodMapper;
import com.example.online_shopping_website.mapper.PromotionMapper;
import com.example.online_shopping_website.mapper.ShopMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class CheckPromotionForClose {
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private GoodMapper goodMapper;
    @Autowired
    private PromotionMapper promotionMapper;
    @Scheduled(initialDelay = 0, fixedRate = 60000)
    public void check(){
        System.out.println("周期性检查促销活动是否该关闭");
        Promotion p = promotionMapper.GetPromotionForCheck();
        if( p == null)
            return;
        Date promotionEndTime = p.getEndTime();
        BigDecimal fundUsed = p.getPromotionFundUsed();
        BigDecimal fundSet = p.getPromotionFund();
        if(promotionEndTime.before(new Date()) || fundUsed.compareTo(fundSet) >= 0){    //促销时间超过，或者资金已经用完了
            promotionMapper.SetCurrentPromotionClosed();
            shopMapper.SetShopNotInPromotion();
            goodMapper.SetGoodsNotInPromotion();
        }

    }
}
