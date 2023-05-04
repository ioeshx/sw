package com.example.online_shopping_website.controller;

import com.example.online_shopping_website.entity.Shop;
import com.example.online_shopping_website.entity.ShopInfo;
import com.example.online_shopping_website.entity.User;
import com.example.online_shopping_website.mapper.UserMapper;
import com.example.online_shopping_website.service.IShopService;
import com.example.online_shopping_website.service.IUserService;
import com.example.online_shopping_website.service.ex.*;
import com.example.online_shopping_website.service.impl.UserServiceImpl;
import com.example.online_shopping_website.util.JsonResult;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static javax.security.auth.callback.ConfirmationCallback.*;

//@Controller
@RestController
public class ShopController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IShopService shopService;
    @RequestMapping("/api/shopRegister")
    public JsonResult<Shop> open(@RequestBody Map<String,Object> map) {

        String shopname = (String)map.get("shopname");
        String goodstype = (String)map.get("goodstype");
        String idnum = (String)map.get("idnum");
        String introduction = (String)map.get("introduction");
        String address = (String)map.get("address");
        String capital = (String)map.get("capital");
        String date = (String) map.get("date");
        String username = (String)map.get("username");

        JsonResult<Shop> result = new JsonResult<>(YES);
        User user = userService.SearchByUsername(username);

        Shop shop = new Shop(shopname,goodstype,idnum,introduction,address,capital,date,user.getUid());
        try {shopService.open(shop);
        } catch (ShopnameDuplicateException e) { // 店名被占用
            result.setState(NO);
            result.setMessage("店名已经被占用");
        } catch (idDuplicateException e){
            result.setState(NO);
            result.setMessage("该身份证已经创建过商店了");
        }
        if(result.getState() == YES){
            result.setData(shop);
        }

        return result;
    }
    @RequestMapping("/api/registrationApproved")
    public JsonResult<Void> registrationApproved(@RequestBody Map<String,Object> userMap) {
        String shopname = (String)userMap.get("shopname");
        JsonResult<Void> result = new JsonResult<Void>();
        //如果输入0 则返回错误
        System.out.println(shopname);
        Shop shop = shopService.shop_admitted(shopname);
        result.setState(YES);
        return result;
    }
    @RequestMapping("/api/getMyShopInfo")
    public  JsonResult<ShopInfo> getMyShopInfo(@RequestBody Map<String,Object> userMap
                                               //@RequestParam("shopname") String shopname
    ){

        String shopname = (String)userMap.get("shopname");
        System.out.println(shopname);
        JsonResult<ShopInfo> checkResult = new JsonResult<>();
        ShopInfo shopInfo = shopService.ShopAdmitAdmittanceCheck(shopname);
        if(shopInfo==null) {
            checkResult.setState(4000);
        }
        if(shopInfo!=null) {
            checkResult.setData(shopInfo);
            checkResult.setState(YES);
        }
        return checkResult;
    }
    @RequestMapping("/api/getRegisteringShop")
    public JsonResult<List<Shop>> getRegisteringShop(){
        JsonResult<List<Shop>> Result = new JsonResult<>();
        List<Shop> shopList = shopService.GetAllNotAdmittedShop();
        if(shopList !=null){
            Result.setState(YES);
            Result.setData(shopList);
        }
        return Result;
    }
    @RequestMapping("/api/getAllShop")
    public  JsonResult<List<Shop>> GetAllShop(){
        JsonResult<List<Shop>> Result = new JsonResult<>();
        List<Shop> shopList = shopService.GetAllShop();
        if(shopList !=null){
            Result.setState(YES);
            Result.setData(shopList);
        }
        return Result;
    }
    @RequestMapping("/api/getRegisteredShop")
    public  JsonResult<List<Shop>> getRegisteredShop(){
        JsonResult<List<Shop>> Result = new JsonResult<>();
        List<Shop> shopList = shopService.GetAllAdmittedShop();
        if(shopList !=null){
            Result.setState(YES);
            Result.setData(shopList);
        }
        return Result;
    }
    @RequestMapping("/api/getShopInfoByUsername")
    public  JsonResult<Shop> GetShopInformation(//@RequestBody User user
                                                @RequestBody Map<String,Object> userMap
                                                //@RequestParam("username") String username
    ){
        String username = (String)userMap.get("username");
        //String username = user.getUsername();
        JsonResult<Shop> Result = new JsonResult<>(YES);
        try {Shop shop = shopService.GetShopInformationFromUser(username);}
        catch (UserNotFoundException e){
            Result.setState(4000);
            Result.setMessage("用户名不存在");
        }catch (ShopNotExistException e){
            Result.setState(4000);
            Result.setMessage("该用户没有商店");
        }
        Result.setData(shopService.GetShopInformationFromUser(username));
        return Result;
    }

    @RequestMapping("/api/getShopInfoByShopname")
    public JsonResult getShopInfoByShopname(@RequestBody Map<String,Object> map){
        String shopname = (String)map.get("shopname");
        JsonResult result = shopService.getShopInfoByShopname(shopname);
        return result;
    }

    @RequestMapping("/api/shopUnregister")
    public JsonResult shopUnregister(@RequestBody Map<String,Object> map){
        String shopname = (String) map.get("shopname");
        JsonResult result = shopService.shopUnregister(shopname);
        return result;
    }

    @RequestMapping("/api/cancelRegister")   //撤销商铺注册/删除申请
    public  JsonResult cancelRegister(@RequestBody Map<String,Object> map){
        String shopname = (String) map.get("shopname");
        int cancelType = (int)map.get("cancelType");
        JsonResult result = shopService.cancelRegister(shopname, cancelType);
        return  result;
    }
    @PostMapping("/api/setShopAvatar")
    public JsonResult<User> setShopAvatar(@RequestParam("image") MultipartFile avatarFile, @RequestParam("shopname") String shopname) throws IOException {
        byte[] avatarData = avatarFile.getBytes();
        shopService.UpdateAvatar(shopname,avatarData);
        JsonResult<User> result = new JsonResult<>(YES, "商店头像上传成功");
        return result;
    }
    @PostMapping("/api/getShopAvatarByShopname")
    public JsonResult<String> getShopAvatarByShopname(@RequestBody Shop shop){
        String shopname = shop.getShopname();
        String image = shopService.GetAvatar(shopname);
        JsonResult<String> result = new JsonResult<>();
        if(image!= null){
            result.setState(YES);
            result.setMessage("商店头像返回成功");
        }else{
            result.setState(NO);
            result.setMessage("商店没有头像");
        }
        result.setData(image);
        return result;
    }
    @PostMapping("/api/getShopAvatar")
    public JsonResult<String> DownloadAvatar(@RequestBody Shop shop){
        String shopname = shop.getShopname();
        String image = shopService.GetAvatar(shopname);
        JsonResult<String> result = new JsonResult<>();
        if(image!= null){
            result.setState(YES);
            result.setMessage("商店头像返回成功");
        }else{
            result.setState(NO);
            result.setMessage("商店没有头像");
        }
        result.setData(image);
        return result;
    }
    @PostMapping("/api/deleteShopAvatar")
    public JsonResult<Integer> deleteShopAvatar(@RequestBody Shop shop){
        String shopname = shop.getShopname();
        JsonResult<Integer> Result = new JsonResult<>(YES);
        shopService.deleteShopAvatar(shopname);
        return Result;
    }

    @RequestMapping("/api/shopApplicationApproved")
    public JsonResult shopApplicationApproved(@RequestBody Map<String,Object> map){
        String shopname = (String)map.get("shopname");
        int approveType = (int)map.get("is_admitted");
        JsonResult result = shopService.shopApplicationApproved(shopname, approveType);
        return result;
    }

    @RequestMapping("/api/shopApplicationRejected")
    public JsonResult shopApplicationRejected(@RequestBody Map<String,Object> map){
        String shopname = (String)map.get("shopname");
        int rejectType = (int)map.get("is_admitted");
        JsonResult result = shopService.shopApplicationRejected(shopname, rejectType);
        return result;
    }
}
