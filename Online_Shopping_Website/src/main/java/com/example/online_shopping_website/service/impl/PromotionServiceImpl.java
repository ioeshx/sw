package com.example.online_shopping_website.service.impl;

import com.example.online_shopping_website.entity.Promotion;
import com.example.online_shopping_website.mapper.PromotionMapper;
import com.example.online_shopping_website.service.IPromotionService;
import com.example.online_shopping_website.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.online_shopping_website.entity.constant.GetPromotionType.*;
import static javax.security.auth.callback.ConfirmationCallback.*;

@Service
public class PromotionServiceImpl implements IPromotionService {
    @Autowired
    private PromotionMapper promotionMapper;

    @Override
    public JsonResult adminStartPromotion(Promotion p){
        int row = promotionMapper.InsertPromotion(p);
        if(row == 1)
            return new JsonResult<>(YES, "开启活动成功");
        else
            return new JsonResult<>(NO, "开启活动失败");
    }

    @Override
    public JsonResult getPromotions(int type){
        List<Promotion> p = new ArrayList<>();
        if(type == GetCurrentPromotionType)
            p = promotionMapper.GetCurrentPromotions();
        else if(type == GetAllPromotionType)
            p = promotionMapper.AdminGetAllPromotions();
        if(!p.isEmpty())
            return new JsonResult<>(YES,"成功", p);
        else
            return new JsonResult<>(NO,"未找到活动记录");
    }

    @Override
    public JsonResult applyForPromotion(String username){
            return new JsonResult<>(NO,"未找到活动记录");
    }
}
