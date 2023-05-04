package com.example.online_shopping_website.service;

import com.example.online_shopping_website.entity.GoodReturn;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
@SpringBootTest
// 启动这个单元测试类 参数是固定的
@RunWith(SpringRunner.class)
public class GoodServicesTests {
    @Autowired
    private IGoodService goodService;
    @Test
    public void picTest(){
        GoodReturn goodReturn = goodService.getEditingGoodsInfo(1);
    }

}
