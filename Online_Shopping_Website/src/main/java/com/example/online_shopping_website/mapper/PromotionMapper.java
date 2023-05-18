package com.example.online_shopping_website.mapper;

import com.example.online_shopping_website.entity.Promotion;
import com.example.online_shopping_website.entity.PromotionApplicant;

import java.util.List;

public interface PromotionMapper {
    int InsertPromotion(Promotion p);

    /**
     * 获取当前进行中的促销活动
     * @return 返回List<Promotion>,里面只有一个Promotion对象
     */
    List<Promotion> GetCurrentPromotions();
    List<Promotion> AdminGetAllPromotions();

    /**
     * 获取当前进行中的促销活动，
     * @return 返回Promotion类
     */
    Promotion GetPromotionForCheck();
    void InsertPromotionApplicant(String username, String shopName);
    List<PromotionApplicant> GetAllPromotionApplicant();
    void SetPromotionApplicationStatus(String username, int status);
    int GetPromotionApplicationStatus(String username);
    int IsPromotionApplicantExist(String username);
    int IsPromotionOngingForNow();
    void SetCurrentPromotionClosed();
    String GetShopNameByUsername(String username);
    void DeleteAllApplicantForClosePromotion();
}
