package com.example.online_shopping_website.service;

import com.example.online_shopping_website.entity.Promotion;
import com.example.online_shopping_website.util.JsonResult;

public interface IPromotionService {
    JsonResult adminStartPromotion(Promotion p);
    JsonResult getPromotions(int type);
    JsonResult applyForPromotion(String username);
    JsonResult adminGetAllApplication();
}
