package com.example.online_shopping_website.mapper;

import com.example.online_shopping_website.entity.pic;

import java.util.List;

public interface PicMapper {
    List<pic> searchPicByGoodsId(int goodsId);
    void picDelete(int goodsId);
    void goodsIdUpdate(int goodsId);
}
