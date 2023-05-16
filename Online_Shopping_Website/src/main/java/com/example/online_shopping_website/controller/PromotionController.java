package com.example.online_shopping_website.controller;

import com.example.online_shopping_website.entity.Promotion;
import com.example.online_shopping_website.service.IPromotionService;
import com.example.online_shopping_website.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PromotionController {
    @Autowired
    private IPromotionService promotionService;

    @RequestMapping("/api/adminStartPromotion")
    public JsonResult adminStartPromotion(@RequestBody Promotion p){
        return promotionService.adminStartPromotion(p);
    }

    @RequestMapping("/api/getPromotions")
    public JsonResult getPromotions(){
        return promotionService.getPromotions();
    }
}
