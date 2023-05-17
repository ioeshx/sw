package com.example.online_shopping_website.service.impl;

import com.example.online_shopping_website.entity.Promotion;
import com.example.online_shopping_website.entity.Shop;
import com.example.online_shopping_website.mapper.*;
import com.example.online_shopping_website.service.IPromotionService;
import com.example.online_shopping_website.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.example.online_shopping_website.entity.constant.GetPromotionType.*;
import static javax.security.auth.callback.ConfirmationCallback.*;

@Service
public class PromotionServiceImpl implements IPromotionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private TransactionMapper transactionMapper;
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
        JsonResult result = new JsonResult<>(YES,"");
        int uid = userMapper.GetUidByUsername(username);
        Shop shop = shopMapper.GetShopByUid(uid);
        Promotion p = promotionMapper.GetPromotionForCheck();
        //1.检查商品是否含有指定的种类，查Shop类
        List<String> allGoodsCategoryOfShop = shopMapper.GetGoodsCategoryByShopName(shop.getShopname());    //商店的所有商品的种类
        String goodsTypeInPromotion = p.getGoodsTypeInPromotion();
        List<String> goodsTypeInPromotionList = List.of(goodsTypeInPromotion.split(";"));             //促销要求的商品种类
        boolean isGoodsTypeValid = false;
        for (String i: allGoodsCategoryOfShop) {
            for(String j : goodsTypeInPromotionList) {
                if (i.contains(j)) {
                    isGoodsTypeValid = true;
                    break;  //跳出内层循环
                }
            }
            if(isGoodsTypeValid)
                break;      //跳出外出循环
        }
        if( !isGoodsTypeValid){ //不满足商品种类的要求
            result.setState(NO);
            result.addMessage("在售的商品种类不符合促销活动的要求，要求如下:");
            for(String i : goodsTypeInPromotionList)
                result.addMessage(i + ",");
            result.addMessage(";");
        }
        //2.注册资金是否满足，查Shop类
        BigDecimal capitalLimit = p.getRegistrationCapitalLimit();
        BigDecimal capital = new BigDecimal(shop.getCapital());
        if(capital.compareTo(capitalLimit) < 0){
            result.setState(NO);
            result.addMessage("注册资金不足！注册资金限制：" + capitalLimit.toString() + ",你的注册资金：" + capital +";");
        }
        //3.月销量是否满足，用order类去查。
        int monthlySalesLimit = p.getMonthlySalesLimit();
        int monthlySales = orderMapper.GetMonthlySalesByShopName(shop.getShopname());
        if(monthlySales < monthlySalesLimit){
            result.setState(NO);
            result.addMessage("商店的月销量不满足要求！当前月销量是" + monthlySales + ",要求的月销量是" + monthlySalesLimit + ";");
        }
        //4.月销售额是否满足,用流水去查
        BigDecimal monthlySaleIncomeLimit = p.getMonthlySaleIncomeLimit();
        BigDecimal monthlySalesIncome = transactionMapper.GetMonthlySalesIncomeByUsername(username);
        if(monthlySalesIncome.compareTo(monthlySaleIncomeLimit) < 0){
            result.setState(NO);
            result.addMessage("商店的月销售额不满足要求!当前的月销售额是" + monthlySalesIncome + ",要求的月销售额是" + monthlySaleIncomeLimit +";" );
        }
        //5.符合所有要求，插入promotionapplicant表，返回申请成功
        if(result.getState() == YES)
            promotionMapper.InsertPromotionApplicant(username, shop.getShopname());
        if(result.getMessage().isEmpty())
            result.setMessage("申请成功");
        return result;
    }

    @Override
    public JsonResult adminGetAllApplication(){
        return new JsonResult<>(NO,"未找到活动记录");
    }
}
