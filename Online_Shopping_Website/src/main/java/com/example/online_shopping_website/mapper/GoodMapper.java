package com.example.online_shopping_website.mapper;

import com.example.online_shopping_website.entity.Good;
import com.example.online_shopping_website.entity.pic;

import java.util.List;

public interface GoodMapper {
    Integer insertGoodByUser(String introduction,String goodsname,float goodsPrice,int goodsStock,String goodsCategory,int status,int registerStatus,int modifyStatus,String shopname);
    int setGood(Good good);
    Good SearchByGoodname(String goodsname);
    List<Good> SearchListByGoodname(String goodsname);
    void UpdateGoodsStock(int goodsId,int goodsStock);
    void UpdateGoodsPrice(int goodsId,float goodsPrice);
    void UpdateGoodsname(int goodsId,String goodsname);
    void UpdateIntroduction(int goodsId,String introduction);
    void UpdateGoodsCategory(int goodsId,String goodsCategory);
    void UpdateStatus(int goodsId,int status);
    void UpdateRegisterStatus(int goodsId,int registerStatus);
    void UpdateModifyStatus(int goodsId,int modifyStatus);
    Integer picInsert(int goodsId,byte[] pic);
    List<Good> SearchByStatus(String shopname, int status);
    List<Good> SearchByStatusStatusOnly(int status);
    Good StatusSearch(int status);
    Good RegisterStatusSearch(int registerStatus);
    List<Good> SearchByRegisterStatusRegisterStatusOnly(int registerStatus);
    List<Good> SearchByModifyStatusModifyStatusOnly(int modifyStatus);

    List<Good> SearchByRegisterStatus(String shopname,int registerStatus);
    List<Good> SearchByModifyStatus(String shopname,int modifyStatus);
    Good SearchByGoodsId(int goodsId);
    void setDelete(int goodsId);
    void AddToFavorite(String username, int goodsId);
    void Unfavorite(String username, int goodsId);
    Boolean isFavorite(String username, int goodsId);
    int GetGoodsStockByGoodsId(int goodsId);
    void DeleteGoods(int goodsId);

    String GetShopnamByGoodsId(int goodsId);

    int GetGoodStatusByGoodsId(int goodsId);

    Good getGoodsByGoodsId(int goodsId);
    Good setSearch(int modifyStatus,String shopname);
}
