package com.example.online_shopping_website.mapper;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public interface CartMapper {
    Boolean IsGoodsInCart(String username, int goodsId);

    void updateCartGoodsNum(String username, int goodsId, int num);

    void insertCartGoodsNum(String username, int goodsId, int num);

    List<Integer> GetAllGoodsIdInCartByUsername(String username);


    Integer getGoodsNumberInCart(String username, int goodsId);

    void InsertNewGoodsIntoCart(String username, int goodsId, int num);

    void UpdateGoodsNumInCart(String username, int goodsId,  int num);

    void DeleteZeroGoodsInCart(String username);

    void DeleteGoodsInCart(String username, int goodsId);

    List<String> getAllShopnameInCart(String username);
}
