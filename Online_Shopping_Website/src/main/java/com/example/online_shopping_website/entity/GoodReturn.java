package com.example.online_shopping_website.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodReturn {
    int goodsId;
    String goodsname;
    float goodsPrice;
    int status;
    int registerStatus;
    int modifyStatus;
    List<String> goodsCategory;
    int goodsStock;
    String introduction;
    String shopname;
    List<String> goodsAvatar;
    int favorites;
}
