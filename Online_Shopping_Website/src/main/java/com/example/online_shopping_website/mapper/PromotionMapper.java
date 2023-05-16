package com.example.online_shopping_website.mapper;

import com.example.online_shopping_website.entity.Promotion;

import java.util.List;

public interface PromotionMapper {
    int InsertPromotion(Promotion p);
    List<Promotion> GetPromotions();
}
