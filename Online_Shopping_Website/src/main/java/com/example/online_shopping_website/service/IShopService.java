package com.example.online_shopping_website.service;
import com.example.online_shopping_website.entity.Shop;
import com.example.online_shopping_website.entity.ShopInfo;
import com.example.online_shopping_website.util.JsonResult;

import java.util.List;

public interface IShopService {
    void open(Shop shop);
    Shop shop_admitted(String shopname);
    List<Shop> GetAllShop();
    List<Shop> GetAllAdmittedShop();
    ShopInfo ShopAdmitAdmittanceCheck(String shopname);
    List<Shop> GetAllNotAdmittedShop();
    Shop GetShopInformationFromUser(String username);
    JsonResult shopUnregister(String shopname);

    JsonResult cancelRegister(String shopname, int cancelType);

    JsonResult getShopInfoByShopname(String shopname);
    void UpdateAvatar(String shopname,byte[] avatar);
    String GetAvatar(String shopname);
    void deleteShopAvatar(String shopname);
    JsonResult shopApplicationApproved(String shopname, int approveType);

    JsonResult shopApplicationRejected(String shopname, int rejectType);
}
