package com.example.online_shopping_website.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopInfo {
    private String shopname;
    private String goodstype;
    private String introduction;

}
