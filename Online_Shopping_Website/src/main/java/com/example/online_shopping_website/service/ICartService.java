package com.example.online_shopping_website.service;

import com.example.online_shopping_website.util.JsonResult;

import java.util.List;

public interface ICartService {
    JsonResult setCartGoodsNum(String username, int goodsId, int num);

    JsonResult getValidCart(String username);

    JsonResult getInvalidCart(String username);

    JsonResult addToCart(String username, int goodsId, int num);

    JsonResult deleteGoodsFromCart(String username, int goodsId);

    JsonResult deleteGoodsListFromCart(String username, List<Integer> goodsList);
}
