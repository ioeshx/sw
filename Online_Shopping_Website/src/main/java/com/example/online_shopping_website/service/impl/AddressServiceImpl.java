package com.example.online_shopping_website.service.impl;

import com.example.online_shopping_website.entity.ReceiverAddress;
import com.example.online_shopping_website.mapper.UserMapper;
import com.example.online_shopping_website.service.IAddressService;
import com.example.online_shopping_website.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static javax.security.auth.callback.ConfirmationCallback.NO;
import static javax.security.auth.callback.ConfirmationCallback.YES;

@Service
public class AddressServiceImpl implements IAddressService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public JsonResult getAllReceiverAddress(String username){
        JsonResult result = new JsonResult<>(YES);
        List<ReceiverAddress> allAddress = userMapper.GetAllReceiverAddress(username);
        result.setData(allAddress);
        return result;
    }

    @Override
    public JsonResult addReceiverAddress(ReceiverAddress newAddress){
        JsonResult result = new JsonResult<>(YES,"成功添加收货地址！");
        userMapper.AddReceiverAddress(newAddress);
        return result;
    }

    @Override
    public JsonResult deleteReceiverAddress(int addressId){
        JsonResult result = new JsonResult<>(YES,"成功删除收货地址！");
        userMapper.DeleteReceiverAddressByAddressID(addressId);
        return result;
    }

    @Override
    public JsonResult modifyReceiverAddress(ReceiverAddress modifiedAddress){
        JsonResult result = new JsonResult<>(YES,"修改地址成功");
        userMapper.ModifyReceiverAddressByAddressId(modifiedAddress);
        return result;
    }

    @Override
    public JsonResult setDefaultReceiverAddress(String username, int addressId){
        JsonResult result = new JsonResult<>(YES, "设置默认地址成功！");
        userMapper.deletePreviousDefaultAddress(username);
        userMapper.setDefaultReceiverAddress(username, addressId);
        return result;
    }
    @Override
    public JsonResult getDefaultReceiverAddress(String username){
        JsonResult result = new JsonResult<>(YES, "");
        ReceiverAddress defaultAddress = userMapper.getDefaultReceiverAddress(username);
        result.setData(defaultAddress);
        return result;
    }

    @Override
    public JsonResult getReceiverAddressByAddressId(int addressId){
        ReceiverAddress address = userMapper.getReceiverAddressByAddressId(addressId);
        if(address == null)
            return new JsonResult<>(NO,"地址ID对应的地址不存在");
        else
            return new JsonResult<>(YES,"",address);
    }

}
