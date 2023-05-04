package com.example.online_shopping_website.controller;

import com.example.online_shopping_website.entity.Shop;
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


}


