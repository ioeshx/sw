package com.example.online_shopping_website.entity;

import lombok.Data;

import java.util.List;

@Data
public class GoodSortByShop {
    private String shopname;

    private List<GoodReturn> goodReturnList;        //同一商品下的商品

    private List<Integer> GoodNums;                 //与上一字段对应的商品数量

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public List<GoodReturn> getGoodReturnList() {
        return goodReturnList;
    }

    public void setGoodReturnList(List<GoodReturn> goodReturnList) {
        this.goodReturnList = goodReturnList;
    }

    public List<Integer> getGoodNums() {
        return GoodNums;
    }

    public void setGoodNums(List<Integer> goodNums) {
        GoodNums = goodNums;
    }

    public GoodSortByShop() {
    }

    public GoodSortByShop(String shopname, List<GoodReturn> goodReturnList) {
        this.shopname = shopname;
        this.goodReturnList = goodReturnList;
    }

    public GoodSortByShop(String shopname, List<GoodReturn> goodReturnList, List<Integer> goodNums) {
        this.shopname = shopname;
        this.goodReturnList = goodReturnList;
        GoodNums = goodNums;
    }
}
