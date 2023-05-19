package com.example.online_shopping_website.controller;

import com.example.online_shopping_website.entity.ReceiverAddress;
import com.example.online_shopping_website.service.IAddressService;
import com.example.online_shopping_website.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddressController {
    @Autowired
    private IAddressService addressService;

    @RequestMapping("/api/getAllReceiverAddress")
    public JsonResult getAllReceiverAddress(@RequestBody Map<String,Object> map){
        String username = (String)map.get("username");
        JsonResult result = addressService.getAllReceiverAddress(username);
        return result;
    }

    @RequestMapping("/api/addReceiverAddress")
    public JsonResult addReceiverAddress(@RequestBody Map<String,Object> map){
        String username = (String)map.get("username");
        String receiverName = (String)map.get("receiverName");
        String phone = (String)map.get("phone");
        String province = (String)map.get("province");
        String municipality = (String)map.get("municipality");
        String county = (String)map.get("county");
        String township = (String)map.get("township");
        String detailAddress = (String)map.get("detailAddress");
        ReceiverAddress newAddress = new ReceiverAddress(username, receiverName, phone, province, municipality, county, township,detailAddress);

        JsonResult result = addressService.addReceiverAddress(newAddress);
        return result;
    }

    @RequestMapping("/api/deleteReceiverAddress")
    public JsonResult deleteReceiverAddress(@RequestBody Map<String,Object> map){
        int addressID = (int) map.get("addressId");
        JsonResult result = addressService.deleteReceiverAddress(addressID);
        return result;
    }

    @RequestMapping("/api/modifyReceiverAddress")
    public JsonResult modifyReceiverAddress(@RequestBody Map<String,Object> map){
        int addressID = (int) map.get("addressId");
        String receiverName = (String)map.get("receiverName");
        String phone = (String)map.get("phone");
        String province = (String)map.get("province");
        String municipality = (String)map.get("municipality");
        String county = (String)map.get("county");
        String township = (String)map.get("township");
        String detailAddress = (String)map.get("detailAddress");
        ReceiverAddress modifiedAddress = new ReceiverAddress(addressID ,receiverName, phone, province, municipality, county, township,detailAddress);

        JsonResult result = addressService.modifyReceiverAddress(modifiedAddress);
        return result;
    }

    @RequestMapping("/api/setDefaultReceiverAddress")
    public JsonResult setDefaultReceiverAddress(@RequestBody Map<String,Object> map){
        String username = (String)map.get("username");
        int addressId = (int)map.get("addressId");
        JsonResult result = addressService.setDefaultReceiverAddress(username, addressId);
        return  result;
    }

    @RequestMapping("/api/getDefaultReceiverAddress")
    public JsonResult getDefaultReceiverAddress(@RequestBody Map<String,Object> map){
        String username = (String)map.get("username");
        JsonResult result = addressService.getDefaultReceiverAddress(username);
        return result;
    }

    @RequestMapping("/api/getReceiverAddressByAddressId")
    public JsonResult getReceiverAddressByAddressId(@RequestBody Map<String,Object> map){
        int addressId = (int)map.get("addressId");
        return addressService.getReceiverAddressByAddressId(addressId);
    }

}
