package com.example.online_shopping_website.controller;

import com.example.online_shopping_website.entity.Good;
import com.example.online_shopping_website.entity.GoodReturn;
import com.example.online_shopping_website.entity.Shop;
import com.example.online_shopping_website.service.IGoodService;
import com.example.online_shopping_website.service.ex.GoodnameDuplicateException;
import com.example.online_shopping_website.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static javax.security.auth.callback.ConfirmationCallback.NO;
import static javax.security.auth.callback.ConfirmationCallback.YES;

@RestController
public class GoodController {
    @Autowired
    private IGoodService goodService;

    @RequestMapping("/api/goodsRegister")
    public JsonResult<Integer> goodsRegister(@RequestBody Good good) {
        JsonResult<Integer> result = new JsonResult<>(YES);
        try {
            Good goodResult = goodService.goodsRegister(good.getIntroduction(),good.getGoodsname(),good.getGoodsPrice(),good.getGoodsStock(),good.getGoodsCategory(),good.getShopname());
            result.setData(goodResult.getGoodsId());
        }catch (GoodnameDuplicateException e) { // 店名被占用
            result.setState(NO);
            result.setMessage("商品名已经被占用");
            result.setData(null);
        }
        return result;
    }
    @RequestMapping("/api/setGoodsInfo")
    public JsonResult<Integer> setGoodsInfo(@RequestBody Good good) {
        JsonResult<Integer> result = new JsonResult<>(YES);
        try {
            goodService.setGoodsInformation(good.getIntroduction(),good.getGoodsname(),good.getGoodsPrice(),good.getGoodsStock(),good.getGoodsCategory(),good.getShopname(),good.getGoodsId());
        }catch(GoodnameDuplicateException e){
            result.setState(NO);
            result.setMessage("商品名已经被占用");
            result.setData(null);
        }
        return result;
    }
    @RequestMapping("/api/setGoodsPicture")
    public JsonResult<Integer> setGoodsPicture(@RequestParam("goodsId") int goodsId, @RequestParam("file") List<MultipartFile> file) throws IOException {
        JsonResult<Integer> result = new JsonResult<>(YES);
        goodService.goodsPicsCheck(goodsId);
        for (MultipartFile f : file) {
            byte[] pic = f.getBytes();
            goodService.picsUpload(goodsId,pic);
        }

        return result;
    }
    @RequestMapping("/api/getRegisteringGoodsByShopname")
    public JsonResult<List<GoodReturn>> getRegisteringGoodsByShopname(@RequestBody Shop shop){
        JsonResult<List<GoodReturn>> result = new JsonResult<>();
        String shopname = shop.getShopname();
        List<GoodReturn> goodList= goodService.searchByRegisterStatus(shopname,0);
        if (goodList == null){
            result.setState(NO);
        }else {
            result.setState(YES);
            result.setData(goodList);
        }
        return result;
    }
    @RequestMapping("/api/getRegisteredGoodsByShopname")
    public JsonResult<List<GoodReturn>> getRegisteredGoodsByShopname(@RequestBody Shop shop){
        JsonResult<List<GoodReturn>> result = new JsonResult<>();
        String shopname = shop.getShopname();
        List<GoodReturn> goodList= goodService.searchByRegisterStatus(shopname,1);
        if (goodList == null){
            result.setState(NO);
        }else {
            result.setState(YES);
            result.setData(goodList);
        }
        return result;
    }
    @RequestMapping("/api/getRegistrationDeniedGoodsByShopname")
    public JsonResult<List<GoodReturn>> getRegistrationDeniedGoodsByShopname(@RequestBody Shop shop){
        JsonResult<List<GoodReturn>> result = new JsonResult<>();
        String shopname = shop.getShopname();
        List<GoodReturn> goodList= goodService.searchByRegisterStatus(shopname,2);
        if (goodList == null){
            result.setState(NO);
        }else {
            result.setState(YES);
            result.setData(goodList);
        }
        return result;
    }
    @RequestMapping("/api/getEditingGoodsByShopname")
    public JsonResult<List<GoodReturn>> getEditingGoodsByShopname(@RequestBody Shop shop){
        JsonResult<List<GoodReturn>> result = new JsonResult<>();
        String shopname = shop.getShopname();
        List<GoodReturn> goodList= goodService.searchByModifyStatus(shopname,1);
        if (goodList == null){
            result.setState(NO);
        }else {
            result.setState(YES);
            result.setData(goodList);
        }
        return result;
    }
    @RequestMapping("/api/getEditedGoodsByShopname")
    public JsonResult<List<GoodReturn>> getEditedGoodsByShopname(@RequestBody Shop shop){
        JsonResult<List<GoodReturn>> result = new JsonResult<>();
        String shopname = shop.getShopname();
        List<GoodReturn> goodList= goodService.searchByModifyStatus(shopname,3);
        if (goodList == null){
            result.setState(NO);
        }else {
            result.setState(YES);
            result.setData(goodList);
        }
        return result;
    }
    @RequestMapping("/api/getEditDeniedGoodsByShopname")
    public JsonResult<List<GoodReturn>> getEditDeniedGoodsByShopname(@RequestBody Shop shop){
        JsonResult<List<GoodReturn>> result = new JsonResult<>();
        String shopname = shop.getShopname();
        List<GoodReturn> goodList= goodService.searchByModifyStatus(shopname,2);
        if (goodList == null){
            result.setState(NO);
        }else {
            result.setState(YES);
            result.setData(goodList);
        }
        return result;
    }
    @RequestMapping("/api/getInvalidGoodsByShopname")
    public JsonResult<List<GoodReturn>> getInvalidGoodsByShopname(@RequestBody Shop shop){
        JsonResult<List<GoodReturn>> result = new JsonResult<>();
        String shopname = shop.getShopname();
        List<GoodReturn> goodList= goodService.searchByStatus(shopname,3);
        if (goodList == null){
            result.setState(NO);
        }else {
            result.setState(YES);
            result.setData(goodList);
        }
        return result;
    }
    @RequestMapping("/api/getValidGoodsByShopname")
    public JsonResult<List<GoodReturn>> getValidGoodsByShopname(@RequestBody Shop shop){
        JsonResult<List<GoodReturn>> result = new JsonResult<>();
        String shopname = shop.getShopname();
        List<GoodReturn> goodList= goodService.searchByStatus(shopname,1);
        if (goodList == null){
            result.setState(NO);
        }else {
            result.setState(YES);
            result.setData(goodList);
        }
        return result;
    }
    @RequestMapping("/api/getApprovingGoods")
    public JsonResult<List<GoodReturn>> getApprovingGoods(){
        JsonResult<List<GoodReturn>> result = new JsonResult<>();
        List<GoodReturn> goodList = goodService.getApprovingGoods();
        if(goodList==null){
            result.setState(NO);
            result.setMessage("没有待批复的商品");
        }else{
            result.setState(YES);
            result.setMessage("返回成功");
            result.setData(goodList);
        }
        return result;
    }
    @RequestMapping("/api/goodsApplicationApproved")
    public JsonResult<Integer> goodsApplicationApproved(@RequestBody Good good){
        JsonResult<Integer> result = new JsonResult<>();
        int Result =  goodService.goodsApplicationApproved(good.getGoodsId(),good.getStatus());
        if (Result ==0){
            result.setState(YES);
            result.setMessage("批准成功");
        }else{
            result.setState(NO);
            result.setMessage("批准失败");
        }
        return result;
    }
    @RequestMapping("/api/goodsApplicationRejected")
    public JsonResult<Integer> goodsApplicationRejected(@RequestBody Good good){
        JsonResult<Integer> result = new JsonResult<>();
        int Result =  goodService.goodsApplicationRejected(good.getGoodsId(),good.getStatus());
        if (Result ==0){
            result.setState(YES);
            result.setMessage("拒绝成功");
        }else{
            result.setState(NO);
            result.setMessage("拒绝失败");
        }
        return result;
    }
    @RequestMapping("/api/goodsOffShelve")
    public JsonResult<Integer> goodsOffShelve(@RequestBody Good good){
        int goodsId = good.getGoodsId();
        JsonResult<Integer> result = new JsonResult<>();
        int Result = goodService.goodsOffShelve(goodsId);
        if(Result ==0){
            result.setState(YES);
            result.setMessage("下架成功");
        }else{
            result.setState(NO);
            result.setMessage("下架失败，该商品并不是正在售卖的正常商品");
        }
        return result;

    }
    @RequestMapping("/api/goodsOnShelve")
    public JsonResult<Integer> goodsOnShelve(@RequestBody Good good){
        int goodsId = good.getGoodsId();
        JsonResult<Integer> result = new JsonResult<>();
        int Result = goodService.goodsOnShelve(goodsId);
        if(Result ==0){
            result.setState(YES);
            result.setMessage("上架成功");
        }else{
            result.setState(NO);
            result.setMessage("上架失败，该商品并未下架");
        }
        return result;
    }
    @RequestMapping("/api/cancelApplication")
    public JsonResult<Integer> cancelApplication(@RequestBody Good good){
        int goodsId = good.getGoodsId();
        JsonResult<Integer> result = new JsonResult<>();
        int Result = goodService.cancelApplication(goodsId);
        if(Result ==0){
            result.setState(YES);
            result.setMessage("取消成功");
        }else{
            result.setState(NO);
            result.setMessage("取消失败");
        }
        return result;
    }
    @RequestMapping("/api/getEditingGoodsInfo")
    public JsonResult<GoodReturn> getEditingGoodsInfo(@RequestBody Good good){
        int goodsId = good.getGoodsId();
        JsonResult<GoodReturn> result = new JsonResult<>();
        GoodReturn goodReturn = goodService.getEditingGoodsInfo(goodsId);
        result.setData(goodReturn);
        result.setState(YES);
        return result;
    }


    @RequestMapping("/api/addToFavorites")
    public JsonResult addToFavorites(@RequestBody Map<String,Object> map){
        String username = (String)map.get("username");
        int goodsId = (int)map.get("goodsId");
        JsonResult result = goodService.addToFavorites(username, goodsId);
        return result;
    }

    @RequestMapping("/api/Unfavorite")
    public JsonResult Unfavorite(@RequestBody Map<String,Object> map){
        String username = (String)map.get("username");
        int goodsId = (int)map.get("goodsId");
        JsonResult result = goodService.Unfavorite(username, goodsId);
        return result;
    }

    @RequestMapping("/api/isFavorite")
    public JsonResult isFavorite(@RequestBody Map<String,Object> map){
        String username = (String)map.get("username");
        int goodsId = (int)map.get("goodsId");
        JsonResult result = goodService.isFavorite(username, goodsId);
        return result;
    }
    @RequestMapping("/api/getGoodsInfoByGoodsId")
    public JsonResult<GoodReturn> getGoodsInfoByGoodsId(@RequestBody Good good){
        int goodsId = good.getGoodsId();
        JsonResult<GoodReturn> result = new JsonResult<>();
        GoodReturn goodReturn = goodService.getGoodsInfoByGoodsId(goodsId);
        if(goodReturn ==null){
            result.setState(NO);
            result.setMessage("没有找到该商品");
        }else {
            result.setState(YES);
            result.setMessage("返回成功");
            result.setData(goodReturn);
        }
        return result;
    }

    @RequestMapping("/api/modifyGoodsPicture")
    public JsonResult<Integer> modifyGoodsPicture(@RequestParam("goodsId") int goodsId, @RequestParam("file") List<MultipartFile> file) throws IOException{
        JsonResult<Integer> result = new JsonResult<>(YES);
        goodService.goodsPicsCheck(-goodsId);
        for (MultipartFile f : file) {
            byte[] pic = f.getBytes();
            goodService.picsUpload(-goodsId,pic);
        }

        return result;
    }

}
