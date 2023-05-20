package com.example.online_shopping_website;

import com.example.online_shopping_website.mapper.PromotionMapper;
import com.example.online_shopping_website.service.impl.OrderServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ClassMethodTest {
    @Autowired
    OrderServiceImpl orderService ;
    @Test
    public void CalculatePricesTest(){
        List<Integer> GoodIDList1 = new ArrayList<>();
        GoodIDList1.add(24);
        GoodIDList1.add(25);
        GoodIDList1.add(26);
        List<Integer> GoodsNum1 = new ArrayList<>();
        GoodsNum1.add(1);
        GoodsNum1.add(1);
        GoodsNum1.add(1);
        List<BigDecimal> ActualResult = orderService.CalculatePrices(GoodIDList1, GoodsNum1);
        List<BigDecimal> ExpectedResult = new ArrayList<>();
        ExpectedResult.add(new BigDecimal("260.0"));
        ExpectedResult.add(new BigDecimal("240.0000"));
        ExpectedResult.add(new BigDecimal("20.0000"));

        Assert.assertArrayEquals(ActualResult.toArray(), ExpectedResult.toArray());
    }

    @Test
    public void isPromotionEffectiveForParentOrderTest(){
        List<Integer> GoodIDList1 = new ArrayList<>();
        GoodIDList1.add(24);
        GoodIDList1.add(25);
        GoodIDList1.add(26);
        List<Integer> GoodsNum1 = new ArrayList<>();
        GoodsNum1.add(1);
        GoodsNum1.add(1);
        GoodsNum1.add(1);
        boolean Actual = orderService.isPromotionEffectiveForParentOrder(GoodIDList1, GoodsNum1);
        Assert.assertTrue(Actual);
    }
}
