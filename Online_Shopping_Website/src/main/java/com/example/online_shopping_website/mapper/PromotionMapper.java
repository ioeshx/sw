package com.example.online_shopping_website.mapper;

import com.example.online_shopping_website.entity.Promotion;
import com.example.online_shopping_website.entity.PromotionApplicant;

import java.util.List;

public interface PromotionMapper {
    int InsertPromotion(Promotion p);
    List<Promotion> GetCurrentPromotions();
    List<Promotion> AdminGetAllPromotions();
    Promotion GetPromotionForCheck();
    void InsertPromotionApplicant(String username, String shopName);
    List<PromotionApplicant> GetAllPromotionApplicant();
    void SetPromotionApplicationStatus(String username, int status);
    int GetPromotionApplicationStatus(String username);
    int IsPromotionApplicantExist(String username);
    int IsPromotionOngingForNow();
    void SetCurrentPromotionClosed();
    String GetShopNameByUsername(String username);
}
