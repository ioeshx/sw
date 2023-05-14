package com.example.online_shopping_website.controller;

import com.example.online_shopping_website.entity.ReceiverAddress;
import com.example.online_shopping_website.entity.User;
import com.example.online_shopping_website.service.IShopService;
import com.example.online_shopping_website.service.IUserService;
import com.example.online_shopping_website.service.ex.*;
import com.example.online_shopping_website.util.JsonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static javax.security.auth.callback.ConfirmationCallback.*;

//@Controller
@RestController
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IShopService shopService;

    @RequestMapping("/api/userRegister")
    public JsonResult<User> register(@RequestBody User user){
        // 不用 try catch
        JsonResult<User> registerResult = userService.register(user);
        return registerResult;
    }

    @RequestMapping("/api/userlogin")
    public JsonResult<User> login(@RequestBody User user){
        JsonResult<User> loginResult = new JsonResult<>();
        try{
            loginResult = userService.login(user.getUsername(), user.getPassword());
        }catch(UserNotFoundException e){
            loginResult.setState(NO);
            loginResult.setMessage("登录失败：用户名不存在！");
        }catch(PasswordNotMatchException e){
            loginResult.setState(NO);
            loginResult.setMessage("登录失败：密码错误！");
        }

        return loginResult;
    }

    @RequestMapping("/api/getUserInfo")
    public JsonResult<User> getUserInfo(@RequestBody User user){
        String username = user.getUsername();
        JsonResult<User> getUserInfoResult = new JsonResult<>(YES);
        try{
            getUserInfoResult = userService.getUserInfo(username);
        }catch(UserNotFoundException e){    //正常是不会出现的用户找不到的情况
            getUserInfoResult.setState(NO);
            System.out.println("用户未找到");
        }catch(UsernameInvalidException e){ //前端检查过了，正常是不会出现的用户名违法的的情况
            getUserInfoResult.setState(NO);
            System.out.println("用户名违法");
        }
        return  getUserInfoResult;
    }

    @RequestMapping("/api/setUserInfo")
    public JsonResult<User> setUserInfo(@RequestBody Map<String,Object> map){
        String oldusername = (String)map.get("oldusername");
        String newusername = (String)map.get("newusername");
        String newpassword = (String)map.get("newpassword");
        String newphone = (String)map.get("newphone");
        String newemail = (String)map.get("newemail");

        JsonResult<User> setUserInfoResult = new JsonResult<>(YES,"");
        User NewUserInfo = new User(newusername,newpassword,newphone,newemail,"");

        try{
            setUserInfoResult = userService.setUserInfo(oldusername, NewUserInfo);
        }catch(UserNotFoundException e){
            setUserInfoResult.setState(NO);
            setUserInfoResult.addMessage("修改失败：用户未找到;");
        } catch(UsernameDuplicatedException e){
            setUserInfoResult.setState(NO);
            setUserInfoResult.addMessage("修改失败：该用户名已其他用户使用;");
        }catch(PhoneDuplicatedException e){
            setUserInfoResult.setState(NO);
            setUserInfoResult.addMessage("修改失败：该手机号已被其他用户使用;");
        }catch(EmailDuplicatedException e){
            setUserInfoResult.setState(NO);
            setUserInfoResult.addMessage("修改失败：该邮箱已被其他用户使用;");
        }catch(SQLException e){             //操作数据库时出错
            setUserInfoResult.setState(NO);
            setUserInfoResult.setMessage("修改失败：出现未知错误;");
        }catch(InfoInvalidException e){     //信息违法
            setUserInfoResult.setState(NO);
            setUserInfoResult.setMessage("修改失败：出现未知错误;");
            System.out.println("前端传来的信息（检查过）有误，可能是恶意攻击");
        }

        return  setUserInfoResult;
    }


//    public JsonResult<User> uploadAvatar(@RequestBody AvatarLoader avatarLoader /*@RequestParam("avatar") MultipartFile avatarFile, @RequestParam("username") String username*/) throws IOException {
//            User user = new User();
//            MultipartFile avatarFile = avatarLoader.getAvatarFile();
//            String username = avatarLoader.getUsername();
//            byte[] avatarData = avatarFile.getBytes();
//            userService.UpdateAvatar(username,avatarData);
//            JsonResult result = new JsonResult<User>(YES,"用户头像上传成功");
//            user.setAvatar(avatarData);
//            return result;
//    }
    @PostMapping("/api/setUserAvatar")
    public JsonResult<User> uploadAvatar(@RequestParam("image") MultipartFile avatarFile,@RequestParam("username") String username) throws IOException {
        User user = new User();
        byte[] avatarData = avatarFile.getBytes();
        userService.UpdateAvatar(username,avatarData);
        JsonResult<User> result = new JsonResult<>(YES, "用户头像上传成功");
        user.setAvatar(avatarData);
        return result;
    }
    @PostMapping("/api/getUserAvatar")
    public JsonResult<String> DownloadAvatar(@RequestBody User user){
        String username = user.getUsername();
        String image = userService.GetAvatar(username);
        JsonResult<String> result = new JsonResult<>();
        if(image!= null){
            result.setState(YES);
            result.setMessage("用户头像返回成功");
        }else{
            result.setState(NO);
            result.setMessage("用户没有头像");
        }
        result.setData(image);
        return result;
    }

    @PostMapping("/api/deleteUserAvatar")
    public JsonResult<Integer> deleteUserAvatar(@RequestBody User user){
        String username = user.getUsername();
        JsonResult<Integer> Result = new JsonResult<>(YES);
        userService.deleteUserAvatar(username);
        return Result;
    }

    @RequestMapping("/api/getShopAccount") //根据用户名获取，而非商店名
    public JsonResult getShopAccount(@RequestBody Map<String,Object> map){
        String username = (String)map.get("username");
        JsonResult result = userService.getShopAccount(username);
        return result;
    }
    //确保数据库中只有一位管理员（只有一行数据usertype=0）
    @RequestMapping("/api/getProfitAccount")
    public JsonResult getProfitAccount(){
        JsonResult result = userService.getProfitAccount();
        return result;
    }
    //确保数据库中只有一位管理员（只有一行数据usertype=0）
    @RequestMapping("/api/getIntermediaryAccount")
    public JsonResult getIntermediaryAccount(){
        JsonResult result = userService.getIntermediaryAccount();
        return result;
    }

    @RequestMapping("/api/recharge")
    public JsonResult recharge(@RequestBody Map<String,Object> map){
        String username = (String)map.get("username");
        BigDecimal credit = new BigDecimal((String) map.get("credit"));
        int accountType = (int)map.get("accountType");

        JsonResult result = userService.recharge(username, credit, accountType);
        return result;
    }

    @RequestMapping("/api/getUserTransactions")
    public JsonResult getUserTransactions(@RequestBody Map<String,Object> map){
        String username = (String)map.get("username");
        JsonResult result = new JsonResult<>();
        result = userService.getUserTransactions(username);
        return result;
    }

    @RequestMapping("/api/getAllReceiverAddress")
    public JsonResult getAllReceiverAddress(@RequestBody Map<String,Object> map){
        String username = (String)map.get("username");
        JsonResult result = userService.getAllReceiverAddress(username);
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

        JsonResult result = userService.addReceiverAddress(newAddress);
        return result;
    }

    @RequestMapping("/api/deleteReceiverAddress")
    public JsonResult deleteReceiverAddress(@RequestBody Map<String,Object> map){
        int addressID = (int) map.get("addressId");
        JsonResult result = userService.deleteReceiverAddress(addressID);
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

        JsonResult result = userService.modifyReceiverAddress(modifiedAddress);
        return result;
    }

    @RequestMapping("/api/setDefaultReceiverAddress")
    public JsonResult setDefaultReceiverAddress(@RequestBody Map<String,Object> map){
        String username = (String)map.get("username");
        int addressId = (int)map.get("addressId");
        JsonResult result = userService.setDefaultReceiverAddress(username, addressId);
        return  result;
    }

    @RequestMapping("/api/getDefaultReceiverAddress")
    public JsonResult getDefaultReceiverAddress(@RequestBody Map<String,Object> map){
        String username = (String)map.get("username");
        JsonResult result = userService.getDefaultReceiverAddress(username);
        return result;
    }

    @RequestMapping("/api/getReceiverAddressByAddressId")
    public JsonResult getReceiverAddressByAddressId(@RequestBody Map<String,Object> map){
        int addressId = (int)map.get("addressId");
        return userService.getReceiverAddressByAddressId(addressId);
    }
//    @RequestMapping("/api/purchaseInGoodsPage")
//    public JsonResult purchaseInGoodsPage(@RequestBody Map<String,Object> map){
//        int goodsId = (int)map.get("goodsId");
//        int goodsNum = (int) map.get("goodsNum");
//        int addressId = (int)map.get("addressId");
//        String username = (String) map.get("username");
//        JsonResult result = userService.purchaseInGoodsPage(username, goodsId, goodsNum, addressId);
//        return result;
//    }

    @RequestMapping("/api/orderCreating")
    public JsonResult orderCreating(@RequestBody Map<String,Object> map){
        List<Integer> allGoodsId = (List<Integer>)map.get("goodsIdList");
        List<Integer> allGoodsNum = (List<Integer>)map.get("numList");
        String username = (String)map.get("username");
        int addressId = (int)map.get("addressId");

        JsonResult result = userService.orderCreating(username, addressId, allGoodsId, allGoodsNum);
        return result;
    }

    @RequestMapping("/api/getOrderByOrderId")
    public JsonResult getOrderByOrderId(@RequestBody Map<String,Object> map){
        int orderId = (int)map.get("OrderId");
        return userService.getOrderByOrderId(orderId);
    }

    @RequestMapping ("/api/getAllOrders")
    public JsonResult getAllOrders(@RequestBody Map<String,Object> map){
        String username = (String)map.get("username");
        return userService.getAllOrders(username);

    }

    @RequestMapping("/api/payment")
    public JsonResult payment(@RequestBody Map<String,Object> map){
        List<Integer> orderIdList = (List<Integer>) map.get("orderIdList");
        return userService.payment(orderIdList);
    }

    @RequestMapping("/api/cancelOrder")
    public JsonResult cancelOrder(@RequestBody Map<String,Object> map){
        List<Integer> orderIdList = (List<Integer>) map.get("orderIdList");
        return userService.cancelOrder(orderIdList);
    }

    @RequestMapping("/api/confirmReceipt")
    public JsonResult confirmReceipt(@RequestBody Map<String,Object> map){
        List<Integer> orderIdList = (List<Integer>) map.get("orderIdList");
        return userService.confirmReceipt(orderIdList);
    }

    @RequestMapping("/api/makingDelivery")
    public JsonResult makingDelivery(@RequestBody Map<String,Object> map){
        List<Integer> orderIdList = (List<Integer>) map.get("orderIdList");
        return userService.makingDelivery(orderIdList);
    }

    @RequestMapping("/api/getOrdersByStatus")
    public JsonResult getOrdersByStatus(@RequestBody Map<String,Object> map){
        String username = (String)map.get("username");
        int status = (int)map.get("status");
        return userService.getOrdersByStatus(username, status);
    }

    @RequestMapping("/api/deleteOrder")
    public JsonResult deleteOrder(@RequestBody Map<String,Object> map){
        List<Integer> orderIdList = (List<Integer>) map.get("orderIdList");
        return userService.deleteOrder(orderIdList);
    }

    @RequestMapping("/api/refundOrder")
    public JsonResult refundOrder(@RequestBody Map<String,Object> map){
        List<Integer> orderIdList = (List<Integer>) map.get("orderIdList");
        return userService.refundOrder(orderIdList);
    }

    @RequestMapping("/api/agreeToRefund")
    public JsonResult agreeToRefund(@RequestBody Map<String,Object> map) {
        List<Integer> orderIdList = (List<Integer>) map.get("orderIdList");
        return userService.agreeToRefund(orderIdList);
    }

    @RequestMapping("/api/getAllOrdersOfShop")
    public JsonResult getAllOrdersOfShop(@RequestBody Map<String,Object> map){
        String shopName = (String)map.get("shopName");
        return userService.getAllOrdersOfShop(shopName);
    }

    @RequestMapping("/api/getOrdersOfShopByStatus")
    public JsonResult getOrdersOfShopByStatus(@RequestBody Map<String,Object> map){
        String shopName = (String)map.get("shopName");
        int status = (int)map.get("status");
        return userService.getOrdersOfShopByStatus(shopName, status);
    }
}


