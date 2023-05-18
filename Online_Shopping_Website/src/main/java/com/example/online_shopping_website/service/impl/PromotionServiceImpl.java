package com.example.online_shopping_website.service.impl;

import com.example.online_shopping_website.entity.Promotion;
import com.example.online_shopping_website.entity.PromotionApplicant;
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
    private GoodMapper goodMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    private PromotionMapper promotionMapper;

    @Override
    public JsonResult adminStartPromotion(Promotion p){
        int row = promotionMapper.InsertPromotion(p);
        BigDecimal fund = p.getPromotionFund();
        //TODO 从利润账户把补助资金转到中间账户
        if( userMapper.isProfitAccountSufficient(fund)){
            userMapper.TransferPromotionFundToIntermediaryAccount(fund);
            return new JsonResult<>(YES, "开启活动成功");
        }else
            return new JsonResult<>(NO, "利润账户的资金不足，无法开启促销活动");
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
        List<PromotionApplicant> applicants = promotionMapper.GetAllPromotionApplicant();
        if(applicants.isEmpty())
            return new JsonResult<>(NO,"未找到申请记录");
        else
            return new JsonResult<>(YES,"成功",applicants);
    }

    void SetVerifiedGoodsInPromotion(String shopName){
        Promotion p = promotionMapper.GetPromotionForCheck();
        List<Integer> allGoodsIdOfShop = goodMapper.GetAllGoodsIDByShopName(shopName);
        //促销活动对商品种类的要求
        String goodsTypeInPromotion = p.getGoodsTypeInPromotion();
        List<String> goodsTypeInPromotionList = List.of(goodsTypeInPromotion.split(";"));
        for(int goodsId : allGoodsIdOfShop){
            //每个商品的种类描述
            String goodsCategory = goodMapper.GetGoodsCategoryByID(goodsId);
            //如果商品的种类描述中含有
            for(String j : goodsTypeInPromotionList)
                if(goodsCategory.contains(j)){
                    goodMapper.SetGoodsInPromotionByGoodsId(goodsId);
                    break;
                }
        }
        return;
    }

    @Override
    public JsonResult adminCheckPromotionApplication(String username, int checkType){
        if(checkType == AdminApprovePromotion){
            promotionMapper.SetPromotionApplicationStatus(username, ApplicationApproved);
            //1.设置商店InPromotion
            String shopName = promotionMapper.GetShopNameByUsername(username);
            shopMapper.SetShopInPromotion(shopName);
            //2.设置商店中符合种类的商品InPromotion
            SetVerifiedGoodsInPromotion(shopName);

        }else if(checkType == AdminRejectPromotion){
            promotionMapper.SetPromotionApplicationStatus(username, ApplicationRejected);
        }else {
            return new JsonResult<>(NO,"checkType不符合要求");
        }
        return new JsonResult<>(YES,"批复成功");
    }

    @Override
    public JsonResult userGetPromotionApplicationResult(String username){
        int isExist = promotionMapper.IsPromotionApplicantExist(username);
        if(isExist == 0)
            return new JsonResult<>(NO, "未找到申请结果，可能你没有进行申请");
        else {
            int status = promotionMapper.GetPromotionApplicationStatus(username);
            return new JsonResult<>(YES,"成功", status);
        }
    }

    @Override
    public JsonResult adminCloseCurrentPromotion(){
        int isPromotionExist = promotionMapper.IsPromotionOngingForNow();
        if(isPromotionExist == 1){          //当前只有一个促销活动
            promotionMapper.SetCurrentPromotionClosed();
            //把商品和商店属性都设置成不再促销中
            shopMapper.SetShopNotInPromotion();
            goodMapper.SetGoodsNotInPromotion();
            //删除promotionapplicant表的所有申请者信息
            promotionMapper.DeleteAllApplicantForClosePromotion();
            return new JsonResult<>(YES,"当前促销活动已结束");
        }else if(isPromotionExist == 0){    //当前没有促销活动
            return new JsonResult<>(NO,"当前没有促销活动正在进行，无法结束活动！");
        }else{                              //当前有多于1个的促销活动
            return new JsonResult<>(NO,"当前有多于1个的促销活动正在进行！");
        }
    }
}
