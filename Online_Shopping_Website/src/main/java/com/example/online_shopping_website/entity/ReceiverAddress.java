package com.example.online_shopping_website.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiverAddress {
    private int addressId;
    private String username;
    private int uid;
    private String receiverName;
    private String phone;
    private String province;        //省级
    private String municipality;    //市级
    private String county;          //县级
    private String township;        //镇级
    private String detailAddress; //详细地址
    private int state;

    public ReceiverAddress(String username, String receiverName, String phone, String province, String municipality, String county, String township, String detailAddress) {
        this.username = username;
        this.receiverName = receiverName;
        this.phone = phone;
        this.province = province;
        this.municipality = municipality;
        this.county = county;
        this.township = township;
        this.detailAddress = detailAddress;
    }

    public ReceiverAddress(int addressId, String receiverName, String phone, String province, String municipality, String county, String township, String detailAddress) {
        this.addressId = addressId;
        this.receiverName = receiverName;
        this.phone = phone;
        this.province = province;
        this.municipality = municipality;
        this.county = county;
        this.township = township;
        this.detailAddress = detailAddress;
    }
}
