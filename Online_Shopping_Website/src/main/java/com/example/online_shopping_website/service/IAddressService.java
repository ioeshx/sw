package com.example.online_shopping_website.service;

import com.example.online_shopping_website.entity.ReceiverAddress;
import com.example.online_shopping_website.util.JsonResult;

public interface IAddressService {
    JsonResult getAllReceiverAddress(String username);
    JsonResult addReceiverAddress(ReceiverAddress newAddress);
    JsonResult deleteReceiverAddress(int addressId);
    JsonResult modifyReceiverAddress(ReceiverAddress modifiedAddress);
    JsonResult setDefaultReceiverAddress(String username, int addressId);
    JsonResult getDefaultReceiverAddress(String username);
    JsonResult getReceiverAddressByAddressId(int addressId);
}
