package com.example.online_shopping_website.service;

import com.example.online_shopping_website.entity.Good;
import com.example.online_shopping_website.entity.GoodReturn;
import com.example.online_shopping_website.entity.pic;
import com.example.online_shopping_website.util.JsonResult;

import java.util.List;

public interface IGoodService {
    Good goodsRegister(String introduction,String goodsname,float goodsPrice,int goodsStock,String goodsCategory,String shopname);
    void setGoodsInformation(String introduction,String goodsname,float goodsPrice,int goodsStock,String goodsCategory,String shopname,int goodsId);
    void picsUpload(int goodsId,byte[] pic);
    List<GoodReturn> searchByStatus(String shopname, int status);
    List<GoodReturn> searchByRegisterStatus(String shopname,int registerStatus);
    List<GoodReturn> searchByModifyStatus(String shopname,int modifyStatus);
    List<pic> picsSearchByGoodsId(int goodsId);
    List<GoodReturn> getApprovingGoods();
    int goodsOffShelve(int goodsId);
    int goodsOnShelve(int goodsId);
    int goodsApplicationApproved(int goodsId,int status);
    int goodsApplicationRejected(int goodsId,int status);
    int cancelApplication(int goodsId);
    GoodReturn getEditingGoodsInfo(int goodsId);
    JsonResult addToFavorites(String username, int goodsId);
    JsonResult Unfavorite(String username, int goodsId);
    JsonResult isFavorite(String username, int goodsId);
    JsonResult setCartGoodsNum(String username, int goodsId, int num);
    GoodReturn getGoodsInfoByGoodsId(int goodsId);
    void goodsPicsCheck(int goodsId);
}
