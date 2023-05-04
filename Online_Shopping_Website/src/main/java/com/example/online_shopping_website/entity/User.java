package com.example.online_shopping_website.entity;

import com.example.online_shopping_website.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.lang.annotation.Inherited;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity implements Serializable {
    private int uid;

    private int usertype;

    private String username;

    private String password;

    private int shopid;

    private String phone;

    private String email;

    private String idnum;

    private int is_delete;

    private String salt;

    private byte[] avatar;

    BigDecimal privateAccount;

    public byte[] getAvatar() {
        return avatar;
    }
    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getUsertype() {
        return usertype;
    }

    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getShopid() {
        return shopid;
    }

    public void setShopid(int shopid) {
        this.shopid = shopid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdnum() {
        return idnum;
    }

    public void setIdnum(String idnum) {
        this.idnum = idnum;
    }

    public int getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(int is_delete) {
        this.is_delete = is_delete;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(int usertype, String username, String password, String phone, String email, String idnum) {
        this.usertype = usertype;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.idnum = idnum;
    }

    public User(int usertype, String username, String password,
                String phone, String email, String idnum, int is_delete, String salt) {
        this.usertype = usertype;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.idnum = idnum;
        this.is_delete = is_delete;
        this.salt = salt;
    }

    public User(String phone, String email, String idnum) {
        this.phone = phone;
        this.email = email;
        this.idnum = idnum;
    }

    public User(String username, String phone, String email, String idnum) {
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.idnum = idnum;
    }

    public User(String username, String password, String phone, String email, String idnum) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.idnum = idnum;
    }

    public BigDecimal getPrivateAccount() {
        return privateAccount;
    }

    public void setPrivateAccount(BigDecimal privateAccount) {
        this.privateAccount = privateAccount;
    }

    public User(String username, String phone, String email, String idnum, BigDecimal privateAccount) {
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.idnum = idnum;
        this.privateAccount = privateAccount;
    }

    public User(String username, String password, String phone, String email, String idnum, BigDecimal privateAccount) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.idnum = idnum;
        this.privateAccount = privateAccount;
    }
}


