package com.example.online_shopping_website.service.impl;

import com.example.online_shopping_website.entity.*;
import com.example.online_shopping_website.mapper.CartMapper;
import com.example.online_shopping_website.mapper.GoodMapper;
import com.example.online_shopping_website.mapper.ShopMapper;
import com.example.online_shopping_website.mapper.PicMapper;
import com.example.online_shopping_website.service.IGoodService;
import com.example.online_shopping_website.service.ex.GoodnameDuplicateException;
import com.example.online_shopping_website.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import static javax.security.auth.callback.ConfirmationCallback.NO;
import static javax.security.auth.callback.ConfirmationCallback.YES;


@Service
public class GoodServiceImpl implements IGoodService {
    @Autowired
    private GoodMapper goodMapper;
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private PicMapper picMapper;

    @Autowired
    private CartMapper cartMapper;
    @Override
    public Good goodsRegister(String introduction,String goodsname,float goodsPrice,int goodsStock,String goodsCategory,String shopname){
        Good good = goodMapper.SearchByGoodname(goodsname);

        if (good != null) {
            throw new GoodnameDuplicateException("尝试注册的商品名[" + goodsname + "]已经被占用");
        }
        goodMapper.insertGoodByUser(introduction,goodsname,goodsPrice,goodsStock,goodsCategory,0,0,0,shopname);
        Good good1 = goodMapper.SearchByGoodname(goodsname);
        return good1;
    }
    @Override
    public void setGoodsInformation(String introduction,String goodsname,float goodsPrice,int goodsStock,String goodsCategory,String shopname,int goodsId){
        Good goodTrue = goodMapper.SearchByGoodsId(goodsId);
        if(goodTrue.getStatus()==1) {
            Good good = goodMapper.SearchByGoodname(goodsname);
            if (good != null && goodTrue.getGoodsId() != good.getGoodsId()) {
                throw new GoodnameDuplicateException("尝试注册的商品名[" + goodsname + "]已经被占用");
            }
            if (shopname == null) {
                shopname = goodTrue.getShopname();
            }
            goodMapper.insertGoodByUser(introduction, goodsname, goodsPrice, goodsStock, goodsCategory, -goodsId, -goodsId, -goodsId, shopname);
            goodMapper.UpdateStatus(goodsId, 2);
            goodMapper.UpdateModifyStatus(goodsId, 1);
        } else if (goodTrue.getStatus()==0) {
            goodMapper.UpdateIntroduction(goodsId,introduction);
            goodMapper.UpdateGoodsname(goodsId,goodsname);
            goodMapper.UpdateGoodsPrice(goodsId,goodsPrice);
            goodMapper.UpdateGoodsStock(goodsId,goodsStock);
            goodMapper.UpdateGoodsCategory(goodsId,goodsCategory);
        } else if(goodTrue.getStatus()==2){
            Good good = goodMapper.SearchByGoodname(goodsname);
            if (good != null && goodTrue.getGoodsId() != good.getGoodsId()) {
                throw new GoodnameDuplicateException("尝试注册的商品名[" + goodsname + "]已经被占用");
            }
            if (shopname == null) {
                shopname = goodTrue.getShopname();
            }
            goodMapper.setDelete(-goodsId);
            goodMapper.insertGoodByUser(introduction, goodsname, goodsPrice, goodsStock, goodsCategory, -goodsId, -goodsId, -goodsId, shopname);
            goodMapper.UpdateStatus(goodsId, 2);
            goodMapper.UpdateModifyStatus(goodsId, 1);
        }
    }
    @Override
    public void picsUpload(int goodsId,byte[] pic){
        goodMapper.picInsert(goodsId,pic);
    }
    @Override
    public List<GoodReturn> searchByStatus(String shopname, int status){
        List<Good> goodslist = goodMapper.SearchByStatus(shopname,status);
        List<GoodReturn> goodReturnList = new ArrayList<>();
        for(Good good : goodslist){
            List<String> piclist = new ArrayList<>();
            GoodReturn goodReturn = new GoodReturn();
            goodReturn.setGoodsPrice(good.getGoodsPrice());
            goodReturn.setGoodsStock(good.getGoodsStock());
            goodReturn.setGoodsId(good.getGoodsId());
            goodReturn.setGoodsname(good.getGoodsname());
            goodReturn.setIntroduction(good.getIntroduction());
            goodReturn.setShopname(good.getShopname());
            goodReturn.setStatus(good.getStatus());
            goodReturn.setRegisterStatus(good.getRegisterStatus());
            goodReturn.setModifyStatus(good.getModifyStatus());
            goodReturn.setGoodsCategory(Arrays.asList(good.getGoodsCategory().split(";")));
            for(pic pics : picMapper.searchPicByGoodsId(good.getGoodsId())){
                byte[] imageData = pics.getPic();
                String base64Image = Base64.getEncoder().encodeToString(imageData);
                piclist.add(base64Image);
            }
            goodReturn.setGoodsAvatar(piclist);
            goodReturnList.add(goodReturn);
        }
        return goodReturnList;
    }
    @Override
    public List<GoodReturn> searchByRegisterStatus(String shopname, int registerStatus){
        List<Good> goodslist = goodMapper.SearchByRegisterStatus(shopname,registerStatus);
        List<GoodReturn> goodReturnList = new ArrayList<>();
        for(Good good : goodslist){
            List<String> piclist = new ArrayList<>();
            GoodReturn goodReturn = new GoodReturn();
            goodReturn.setGoodsPrice(good.getGoodsPrice());
            goodReturn.setGoodsStock(good.getGoodsStock());
            goodReturn.setGoodsId(good.getGoodsId());
            goodReturn.setGoodsname(good.getGoodsname());
            goodReturn.setIntroduction(good.getIntroduction());
            goodReturn.setShopname(good.getShopname());
            goodReturn.setStatus(good.getStatus());
            goodReturn.setRegisterStatus(good.getRegisterStatus());
            goodReturn.setModifyStatus(good.getModifyStatus());
            goodReturn.setGoodsCategory(Arrays.asList(good.getGoodsCategory().split(";")));
            for(pic pics : picMapper.searchPicByGoodsId(good.getGoodsId())){
                //.out.println(good.getGoodsId());

                byte[] imageData = pics.getPic();
                String base64Image = Base64.getEncoder().encodeToString(imageData);
                //System.out.println(base64Image.substring(0,100));
                piclist.add(base64Image);
            }
            goodReturn.setGoodsAvatar(piclist);
            goodReturnList.add(goodReturn);
        }
        return goodReturnList;
    }
    @Override
    public List<GoodReturn> searchByModifyStatus(String shopname, int modifyStatus){
        List<Good> goodslist = goodMapper.SearchByModifyStatus(shopname,modifyStatus);
        List<GoodReturn> goodReturnList = new ArrayList<>();
        if(modifyStatus==2||modifyStatus==3) {
            for (Good good : goodslist) {
                List<String> piclist = new ArrayList<>();
                GoodReturn goodReturn = new GoodReturn();
                goodReturn.setGoodsPrice(good.getGoodsPrice());
                goodReturn.setGoodsStock(good.getGoodsStock());
                goodReturn.setGoodsId(good.getGoodsId());
                goodReturn.setGoodsname(good.getGoodsname());
                goodReturn.setIntroduction(good.getIntroduction());
                goodReturn.setShopname(good.getShopname());
                goodReturn.setStatus(good.getStatus());
                goodReturn.setRegisterStatus(good.getRegisterStatus());
                goodReturn.setModifyStatus(good.getModifyStatus());
                goodReturn.setGoodsCategory(Arrays.asList(good.getGoodsCategory().split(";")));
                for (pic pics : picMapper.searchPicByGoodsId(good.getGoodsId())) {
                    byte[] imageData = pics.getPic();
                    String base64Image = Base64.getEncoder().encodeToString(imageData);
                    piclist.add(base64Image);
                }
                goodReturn.setGoodsAvatar(piclist);
                goodReturnList.add(goodReturn);
            }
        }else if(modifyStatus ==1){
            goodslist.clear();
            for (int i = 1; i < 100; i++) {
                Good result = goodMapper.setSearch(-i, shopname);
                if (result != null) {
                    if(goodMapper.getGoodsByGoodsId(-result.getRegisterStatus()).getModifyStatus()!=2) {
                        goodslist.add(result);
                    }
                }
            }
            for (Good good : goodslist) {
                List<String> piclist = new ArrayList<>();
                GoodReturn goodReturn = new GoodReturn();
                goodReturn.setGoodsPrice(good.getGoodsPrice());
                goodReturn.setGoodsStock(good.getGoodsStock());
                goodReturn.setGoodsId(good.getGoodsId());
                goodReturn.setGoodsname(good.getGoodsname());
                goodReturn.setIntroduction(good.getIntroduction());
                goodReturn.setShopname(good.getShopname());
                if(good.getStatus()<0){
                    goodReturn.setStatus(2);
                }else goodReturn.setStatus(good.getStatus());
                goodReturn.setRegisterStatus(good.getRegisterStatus());
                goodReturn.setModifyStatus(good.getModifyStatus());
                System.out.println(good.getModifyStatus());
                goodReturn.setGoodsCategory(Arrays.asList(good.getGoodsCategory().split(";")));
                for (pic pics : picMapper.searchPicByGoodsId(good.getModifyStatus())) {
                    byte[] imageData = pics.getPic();
                    String base64Image = Base64.getEncoder().encodeToString(imageData);
                    piclist.add(base64Image);

                }
                System.out.println(piclist.size());
                goodReturn.setGoodsAvatar(piclist);
                goodReturnList.add(goodReturn);
            }
        }
        return goodReturnList;
    }
    @Override
    public List<pic> picsSearchByGoodsId(int goodsId){
        List<pic> piclist = picMapper.searchPicByGoodsId(goodsId);
        return piclist;
    }
    @Override
    public List<GoodReturn> getApprovingGoods(){
        List<Good> goodListA = goodMapper.SearchByRegisterStatusRegisterStatusOnly(0);
        List<Good> goodListB = goodMapper.SearchByModifyStatusModifyStatusOnly(1);
        List<Good> goodList = new ArrayList<>();
        goodList.addAll(goodListA);
        for(Good good :goodListB){
            Good goodAdd = goodMapper.StatusSearch(-good.getGoodsId());
            if(goodAdd!=null) goodList.add(goodAdd);
        }
        List<GoodReturn> goodReturnList = new ArrayList<>();
        for(Good good : goodList){
            GoodReturn goodReturn = new GoodReturn();
            goodReturn.setGoodsPrice(good.getGoodsPrice());
            goodReturn.setGoodsStock(good.getGoodsStock());
            goodReturn.setGoodsId(good.getGoodsId());
            goodReturn.setGoodsname(good.getGoodsname());
            goodReturn.setIntroduction(good.getIntroduction());
            goodReturn.setShopname(good.getShopname());
            if(good.getStatus()<0){
                goodReturn.setStatus(2);
            }else goodReturn.setStatus(good.getStatus());
            goodReturn.setRegisterStatus(good.getRegisterStatus());
            goodReturn.setModifyStatus(good.getModifyStatus());
            goodReturn.setGoodsCategory(Arrays.asList(good.getGoodsCategory().split(";")));
            List<String> piclist = new ArrayList<>();
            if(good.getStatus()<0){
                for (pic pics : picMapper.searchPicByGoodsId(good.getModifyStatus())) {
                    byte[] imageData = pics.getPic();
                    String base64Image = Base64.getEncoder().encodeToString(imageData);
                    piclist.add(base64Image);
                }

            }else {
                for (pic pics : picMapper.searchPicByGoodsId(good.getGoodsId())) {
                    byte[] imageData = pics.getPic();
                    String base64Image = Base64.getEncoder().encodeToString(imageData);
                    piclist.add(base64Image);
                }
            }
            goodReturn.setGoodsAvatar(piclist);
            goodReturnList.add(goodReturn);
        }
        return goodReturnList;
    }
    @Override
    public int goodsOffShelve(int goodsId){
        Good good = goodMapper.SearchByGoodsId(goodsId);
        if(good.getStatus()==1) {
            goodMapper.UpdateStatus(goodsId,3);
            return 0;
        }
        else return 1;
    }
    @Override
    public int goodsOnShelve(int goodsId){
        Good good = goodMapper.SearchByGoodsId(goodsId);
        if(good.getStatus()==3) {
            goodMapper.UpdateStatus(goodsId,1);
            return 0;
        }
        else return 1;
    }
    @Override
    public int goodsApplicationApproved(int goodsId,int status){
        if(status == 0 ){
            goodMapper.UpdateStatus(goodsId,1);
            goodMapper.UpdateRegisterStatus(goodsId,1);
            return 0;
        }
        if(status == 2 ){
            Good good = goodMapper.SearchByGoodsId(goodsId);
            goodMapper.DeleteGoods(goodsId);
            goodsId = -good.getRegisterStatus();
            System.out.println("goodsApplicationApproved:goodsId:");
            System.out.println(goodsId);
            Good goodTrue = goodMapper.getGoodsByGoodsId(goodsId);
            goodMapper.UpdateIntroduction(goodsId,good.getIntroduction());
            goodMapper.UpdateGoodsStock(goodsId,good.getGoodsStock());
            goodMapper.UpdateGoodsname(goodsId,good.getGoodsname());
            goodMapper.UpdateGoodsCategory(goodsId,good.getGoodsCategory());
            goodMapper.UpdateGoodsPrice(goodsId,good.getGoodsPrice());
            goodMapper.UpdateStatus(goodsId,1);
            goodMapper.UpdateRegisterStatus(goodsId,1);
            goodMapper.UpdateModifyStatus(goodsId,3);
            List<pic> picList = picMapper.searchPicByGoodsId(good.getRegisterStatus());
            picMapper.picDelete(goodsId);
            picMapper.goodsIdUpdate(-goodsId);
            return 0;
        }else {
            return 1;
        }
    }
    @Override
    public int goodsApplicationRejected(int goodsId,int status){
        if(status == 0 ){
            goodMapper.UpdateRegisterStatus(goodsId,2);
            return 0;
        }
        if(status == 2 ){
            Good good = goodMapper.SearchByGoodsId(goodsId);//good是样本
            goodsId = -good.getModifyStatus();//goodsId现在是原来真的数据的ID
            goodMapper.UpdateModifyStatus(goodsId,2);
            /*
             * TODO*/

            return 0;
        }else {
            return 1;
        }
    }
    @Override
    public int cancelApplication(int goodsId){
        Good good = goodMapper.SearchByGoodsId(goodsId);
        if(good.getStatus() == 0|| good.getStatus() == 4){
            goodMapper.DeleteGoods(goodsId);
            return 0;
        }
        if(good.getStatus() == 2 ){
            goodMapper.UpdateStatus(goodsId,1);
            goodMapper.UpdateModifyStatus(goodsId,0);

            goodMapper.setDelete(goodsId);
            return 0;
        }else {
            return 1;
        }
    }
    @Override
    public GoodReturn getEditingGoodsInfo(int goodsId){
        Good good = goodMapper.SearchByGoodsId(goodsId);
        GoodReturn goodReturn = new GoodReturn();
        if(good.getStatus() == 1||good.getStatus() == 0){
            goodReturn.setGoodsPrice(good.getGoodsPrice());
            goodReturn.setGoodsStock(good.getGoodsStock());
            goodReturn.setGoodsId(good.getGoodsId());
            goodReturn.setGoodsname(good.getGoodsname());
            goodReturn.setIntroduction(good.getIntroduction());
            goodReturn.setShopname(good.getShopname());
            goodReturn.setStatus(good.getStatus());
            goodReturn.setRegisterStatus(good.getRegisterStatus());
            goodReturn.setModifyStatus(good.getModifyStatus());
            goodReturn.setGoodsCategory(Arrays.asList(good.getGoodsCategory().split(";")));
            List<String> piclist = new ArrayList<>();
            List<pic> picList = picMapper.searchPicByGoodsId(good.getGoodsId());
            for(pic pics : picList){
                byte[] imageData = pics.getPic();
                String base64Image = Base64.getEncoder().encodeToString(imageData);
                piclist.add(base64Image);
            }
            goodReturn.setGoodsAvatar(piclist);
        }else if(good.getStatus() == 2){
            good = goodMapper.StatusSearch(-goodsId);
            goodReturn.setGoodsPrice(good.getGoodsPrice());
            goodReturn.setGoodsStock(good.getGoodsStock());
            goodReturn.setGoodsId(good.getGoodsId());
            goodReturn.setGoodsname(good.getGoodsname());
            goodReturn.setIntroduction(good.getIntroduction());
            goodReturn.setShopname(good.getShopname());
            goodReturn.setStatus(good.getStatus());
            goodReturn.setRegisterStatus(good.getRegisterStatus());
            goodReturn.setModifyStatus(good.getModifyStatus());
            goodReturn.setGoodsCategory(Arrays.asList(good.getGoodsCategory().split(";")));
            List<String> piclist = new ArrayList<>();
            for(pic pics : picMapper.searchPicByGoodsId(good.getGoodsId())){
                byte[] imageData = pics.getPic();
                String base64Image = Base64.getEncoder().encodeToString(imageData);
                piclist.add(base64Image);
            }
            goodReturn.setGoodsAvatar(piclist);
        }
        return goodReturn;
    }

    //在数据表favorite插入一次收藏记录。相应的在goods表对favorite num字段加一
    @Override
    public JsonResult addToFavorites(String username, int goodsId){
        JsonResult result = new JsonResult<>(YES);
        goodMapper.AddToFavorite(username, goodsId);
        return result;
    }
    //在数据表favorite删除相应的收藏记录。相应的在goods表对favorite num字段减一
    @Override
    public JsonResult Unfavorite(String username, int goodsId){
        JsonResult result = new JsonResult<>(YES);
        goodMapper.Unfavorite(username, goodsId);
        return result;
    }

    @Override
    public JsonResult isFavorite(String username, int goodsId){
        JsonResult result = new JsonResult<>(YES);
        Boolean isExist = goodMapper.isFavorite(username, goodsId);
        if(!isExist)
            result.setState(NO);
        return result;
    }
    @Override
    public GoodReturn getGoodsInfoByGoodsId(int goodsId){
        Good good = goodMapper.SearchByGoodsId(goodsId);
        GoodReturn goodReturn = new GoodReturn();
        goodReturn.setGoodsPrice(good.getGoodsPrice());
        goodReturn.setGoodsStock(good.getGoodsStock());
        goodReturn.setGoodsId(good.getGoodsId());
        goodReturn.setGoodsname(good.getGoodsname());
        goodReturn.setIntroduction(good.getIntroduction());
        goodReturn.setShopname(good.getShopname());
        goodReturn.setStatus(good.getStatus());
        goodReturn.setRegisterStatus(good.getRegisterStatus());
        goodReturn.setModifyStatus(good.getModifyStatus());
        goodReturn.setGoodsCategory(Arrays.asList(good.getGoodsCategory().split(";")));
        goodReturn.setFavorites(good.getFavorites());
        List<String> piclist = new ArrayList<>();
        List<pic> picList = picMapper.searchPicByGoodsId(good.getGoodsId());
        for(pic pics : picList){
            byte[] imageData = pics.getPic();
            String base64Image = Base64.getEncoder().encodeToString(imageData);
            piclist.add(base64Image);
        }
        goodReturn.setGoodsAvatar(piclist);
        return goodReturn;
    }

    @Override
    public JsonResult setCartGoodsNum(String username, int goodsId, int num){
        JsonResult result =new JsonResult<>(YES);
        int goodsStock = goodMapper.GetGoodsStockByGoodsId(goodsId);
        if(num > goodsStock)    //异常1：添加到购物车的数量大于库存数量
            result.setState(NO);
        else{
            if(cartMapper.IsGoodsInCart(username,goodsId))  //异常2：用户名下的购物车已经有相应的商品了
                cartMapper.updateCartGoodsNum(username, goodsId, num);  //购物车已有，更新数据库
            else
                cartMapper.insertCartGoodsNum(username, goodsId, num);  //购物车没有，插入数据库
        }
        return result;
    }
    @Override
    public void goodsPicsCheck(int goodsId){
        List<pic> picList = picMapper.searchPicByGoodsId(goodsId);
        if(picList!=null) picMapper.picDelete(goodsId);

    }
}
