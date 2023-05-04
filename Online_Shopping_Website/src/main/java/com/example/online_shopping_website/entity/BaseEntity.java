package com.example.online_shopping_website.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 本项目一切实体的基类
 * 这四个属性都没用到（2023年4月1日）
 */
//@Data
public class BaseEntity implements Serializable {
    private String createdUser;
    private  Date createTime;
    private String modifiedUser;
    private  Date modifiedTime;

}
