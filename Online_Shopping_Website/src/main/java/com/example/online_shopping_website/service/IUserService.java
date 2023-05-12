package com.example.online_shopping_website.service;

import com.example.online_shopping_website.entity.ReceiverAddress;
import com.example.online_shopping_website.entity.User;
import com.example.online_shopping_website.util.JsonResult;

import java.math.BigDecimal;
import java.util.List;

public interface IUserService {
    /**
     * 用户注册功能
     * @param user 用户的信息，封装在类中
     * @return 错误信息
     */
    JsonResult<User> register(User user);

    /**
     * 用户登陆功能
     * @param username 用户名
     * @param password 用户密码
     * @return 返回用户数据
     */
    JsonResult<User> login(String username, String password);

    /**
     * 根据用户名查找用户
     * @return 成功返回用户信息，失败返回空
     */
    User SearchByUsername(String username);

    JsonResult<User> getUserInfo(String username);

    JsonResult<User> setUserInfo(String oldUsername, User NewUserInfo);

    void UpdateAvatar(String username,byte[] avatar);
    String GetAvatar(String username);
    JsonResult recharge(String username, BigDecimal credit,int accountType);

    JsonResult getUserTransactions(String username);

    JsonResult getShopAccount(String username);

    JsonResult getProfitAccount();

    JsonResult getIntermediaryAccount();
    void deleteUserAvatar(String username);
    JsonResult getAllReceiverAddress(String username);
    JsonResult addReceiverAddress(ReceiverAddress newAddress);
    JsonResult deleteReceiverAddress(int addressId);
    JsonResult modifyReceiverAddress(ReceiverAddress modifiedAddress);
    JsonResult setDefaultReceiverAddress(String username, int addressId);
    JsonResult getDefaultReceiverAddress(String username);

    //JsonResult purchaseInGoodsPage(String username, int goodsId, int goodsNum, int addressId);
    //JsonResult purchaseInCart(String username, List<Integer> allGoodsId);

    JsonResult submitOrder(String username, int addressId, List<Integer> allGoodsId, List<Integer> allGoodsNum);
}
