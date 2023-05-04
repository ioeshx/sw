package com.example.online_shopping_website.controller;

import com.example.online_shopping_website.service.ICartService;
import com.example.online_shopping_website.util.JsonResult;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static javax.security.auth.callback.ConfirmationCallback.*;

@RestController
public class CartController {

    @Autowired
    private ICartService cartService;

    //在购物车页面，设置商品数量
    @RequestMapping("/api/setCartGoodsNum")
    public JsonResult setCartGoodsNum(@RequestBody Map<String,Object> map){
        String username = (String)map.get("username");
        int goodsId = (int)map.get("goodsId");
        int num = (int)map.get("num");
        JsonResult result = cartService.setCartGoodsNum(username, goodsId, num);
        return result;
    }

    //在商品页面，把商品添加到购物车
    @RequestMapping("/api/addToCart")
    public JsonResult addToCart(@RequestBody Map<String,Object> map){
        String username = (String)map.get("username");
        int goodsId = (int)map.get("goodsId");
        int num = (int)map.get("num");

        JsonResult result = cartService.addToCart(username, goodsId, num);
        return result;
    }

    @RequestMapping("/api/getValidCart")
    JsonResult getValidCart(@RequestBody Map<String,Object> map){
        String username = (String)map.get("username");
        JsonResult result = cartService.getValidCart(username);
        return result;
    }

    @RequestMapping("/api/getInvalidCart")
    JsonResult getInvalidCart(@RequestBody Map<String,Object> map){
        String username = (String)map.get("username");
        JsonResult result = cartService.getInvalidCart(username);
        return result;
    }

    @RequestMapping("/api/deleteGoodsFromCart")
    public JsonResult deleteGoodsFromCart(@RequestBody Map<String,Object> map){
        String username =(String)map.get("username");
        int goodsId = (int)map.get("goodsId");
        JsonResult result = cartService.deleteGoodsFromCart(username, goodsId);
        return result;
    }

    @RequestMapping("/api/deleteGoodsListFromCart")
    public JsonResult deleteGoodsListFromCart(@RequestBody Map<String,Object> map){
        String username =(String)map.get("username");
        List<Integer> goodsList = (List<Integer>)map.get("goodsList");
        JsonResult result = cartService.deleteGoodsListFromCart(username, goodsList);
        return result;
    }
}
