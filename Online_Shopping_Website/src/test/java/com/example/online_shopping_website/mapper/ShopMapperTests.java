package com.example.online_shopping_website.mapper;

import com.example.online_shopping_website.entity.Shop;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.example.online_shopping_website.entity.User;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ShopMapperTests {
    @Autowired
    private ShopMapper shopMapper;
    @Test
    public void shopOpening() {

        Shop shop = new Shop("shoptest000","apple;banana", "1234567", "this is shop", "fudan", "100", 6);
        int rows = shopMapper.shopOpening(shop);
        System.out.println(rows);
    }
    @Test
    public void SearchByShopname(){
        Shop shop = shopMapper.SearchByShopname("shoptest00");
        System.out.println(shop);
    }
    @Test
    public void updateIsAdmitted(){
        shopMapper.updateIsAdmitted("shoptest00");
    }
    @Test
    public void ShowAllShop(){
        List<Shop> shopList = shopMapper.ShowAllShop();
        System.out.println(shopList);
    }
    @Test
    public void ShowAllAdmittedShop(){
        List<Shop> shopList = shopMapper.ShowAllAdmittedShop();
        System.out.println(shopList);
    }
    @Test
    public void ShowAllNotAdmittedShop(){
        List<Shop> shopList = shopMapper.ShowAllNotAdmittedShop();
        System.out.println(shopList);

    }
    @Test
    public void GetShopInformationFromUser(){
        Shop shop =shopMapper.ShopSearchByUid(6);
        System.out.println(shop);
    }
}
