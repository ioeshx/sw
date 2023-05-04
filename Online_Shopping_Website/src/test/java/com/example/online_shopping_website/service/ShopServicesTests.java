package com.example.online_shopping_website.service;

import com.example.online_shopping_website.entity.Shop;
import com.example.online_shopping_website.entity.ShopInfo;
import com.example.online_shopping_website.util.JsonResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
@SpringBootTest
// 启动这个单元测试类 参数是固定的
@RunWith(SpringRunner.class)
public class ShopServicesTests {
    @Autowired
    private IShopService shopService;
    @Test
    public void open(){
        Shop shop = new Shop("shoptest4",5);
        shopService.open(shop);
    }
    @Test
    public void shop_admitted(){
        shopService.shop_admitted("shoptest2");
    }
    @Test
    public void GetAllShop(){
        List<Shop> shopList = shopService.GetAllShop();
        System.out.println(shopList);
    }
    @Test
    public void ShopAdmitAdmittanceCheck(){
        ShopInfo shopInfo = shopService.ShopAdmitAdmittanceCheck("shoptest4");
        System.out.println(shopInfo);
    }
    @Test
    public void GetShopInformationFromUser(){
        Shop shop = shopService.GetShopInformationFromUser("test1123");
        System.out.println(shop);
    }
}
