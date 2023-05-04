package com.example.online_shopping_website.service.impl;

import com.example.online_shopping_website.entity.Good;
import com.example.online_shopping_website.entity.Transaction;
import com.example.online_shopping_website.mapper.TransactionMapper;
import com.example.online_shopping_website.mapper.UserMapper;
import com.example.online_shopping_website.entity.User;
import com.example.online_shopping_website.service.IUserService;
import com.example.online_shopping_website.service.ex.*;
import com.example.online_shopping_website.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;

import static com.example.online_shopping_website.entity.constant.AccountType.*;
import static com.example.online_shopping_website.entity.constant.UserType.*;
import static javax.security.auth.callback.ConfirmationCallback.*;


/**用户模块业务层的实现类*/
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TransactionMapper transactionMapper;
    @Override
    public JsonResult<User> register(User user){

        JsonResult<User> registerResult = new JsonResult<>(YES,"");
        //对注册信息的双重检查
//        if(!InfoVerification.checkAllInfoValid(user)){
//            throw new InfoInvalidException("前端传来的信息（检查过）有误，可能是恶意攻击");
//        }

        if( userMapper.SearchByUsername(user.getUsername()) != null){
            registerResult.setState(NO);
            registerResult.setMessage("注册失败：用户名已被占用&");
            //throw new UsernameDuplicatedException("注册失败：用户名已被占用");
        }
        if( userMapper.SearchByPhone(user.getPhone()) != null){
            registerResult.setState(NO);
            registerResult.addMessage("注册失败：手机号已被注册&");
            //throw new PhoneDuplicatedException("注册失败：手机号已被注册");
        }
        if( userMapper.SearchByEmail(user.getEmail()) != null){
            registerResult.setState(NO);
            registerResult.addMessage("注册失败：邮箱已被注册&");
            //throw new EmailDuplicatedException("注册失败：邮箱已被注册");
        }
        if(userMapper.SearchByIdnum(user.getIdnum()) != null){
            registerResult.setState(NO);
            registerResult.addMessage("注册失败：身份证号已被注册&");
            //throw new UserIdnumDuplicatedException("注册失败：身份证号已被注册\n");
        }

        if( registerResult.getState() == YES ){
            int row = userMapper.Register(user);
            registerResult.setMessage("注册成功");
            if (row != 1){
                registerResult.setState(NO);
                registerResult.addMessage("注册时出现未知错误;");
                //throw new SQLException("注册时出现未知错误");
            }
        }

        return registerResult;
    }

    @Override
    public User SearchByUsername(String username){
        return userMapper.SearchByUsername(username);
    }

    @Override
    public JsonResult<User> login(String username, String password) {

        String message = "登录成功！";
        User data;

        User result = userMapper.SearchByUsername(username);
        if(result == null){
            throw new UserNotFoundException("登录失败：用户名不存在！");
        }else{
            String oldPassword = result.getPassword();
            if(!oldPassword.equals(password)){
                throw new PasswordNotMatchException("登录失败：密码错误！");
            }else{
                data = userMapper.SearchByUsername(username);
            }
        }

        return new JsonResult<>(YES,message,data);
    }

    @Override
    public JsonResult<User> getUserInfo(String username){

        JsonResult<User> getUserInfoResult = new JsonResult<>(YES);
//        if(!InfoVerification.isUsernameValid(username)){
//            throw new UsernameInvalidException("用户名异常");
//        }
        User user = userMapper.SearchByUsername(username);
        if(user == null){
            throw new UserNotFoundException("用户未找到");
        }
        User userInfoPart = new User(user.getUsername(),user.getPassword(),user.getPhone(), user.getEmail(), user.getIdnum(),user.getPrivateAccount());
        getUserInfoResult.setData(userInfoPart);
        return getUserInfoResult;
    }

    @Override
    public JsonResult<User> setUserInfo(String oldUsername, User NewUserInfo){
        JsonResult<User> setUserInfoResult = new JsonResult<>(YES);
        //两种情况1. 不修改（与原来的信息一样） 2.修改
        //根据新信息找到一行数据，并且新信息与旧信息不一样，就说明存在其他用户的信息与新信息一样
        //比较String 用equals方法 不能用!=
        User SearchByOldusernameResult = userMapper.SearchByUsername(oldUsername);
        if(SearchByOldusernameResult == null)
            throw new UserNotFoundException("旧用户名不存在");
        if( !NewUserInfo.getUsername().equals(oldUsername) && userMapper.SearchByUsername(NewUserInfo.getUsername()) != null)
            throw new UsernameDuplicatedException("新用户名已存在");
        if( !NewUserInfo.getPhone().equals(SearchByOldusernameResult.getPhone())  && userMapper.SearchByPhone(NewUserInfo.getPhone()) != null )
            throw new PhoneDuplicatedException("新电话号码已存在");
        if( !NewUserInfo.getEmail().equals(SearchByOldusernameResult.getEmail())  && userMapper.SearchByEmail(NewUserInfo.getEmail()) != null )
            throw new EmailDuplicatedException("新电子邮箱已存在");
        userMapper.UpdateNewpasswordByOldusername(oldUsername, NewUserInfo.getPassword());
        userMapper.UpdateNewphoneByOldusername(oldUsername, NewUserInfo.getPhone());
        userMapper.UpdateNewemailByOldusername(oldUsername, NewUserInfo.getEmail());
        userMapper.UpdateNewusernameByOldusername(oldUsername, NewUserInfo.getUsername());
        setUserInfoResult.setMessage("修改成功");
        return setUserInfoResult;
    }

    @Override
    public void UpdateAvatar(String username,byte[] avatar){
        userMapper.AvatarUpdate(username,avatar);
    }
    @Override
    public String GetAvatar(String username){
        User user = userMapper.SearchByUsername(username);
        byte[] imageData = user.getAvatar();
        if(imageData==null) return null;
        String base64Image = Base64.getEncoder().encodeToString(imageData);
        return base64Image;
    }

    @Override
    public JsonResult recharge(String username, BigDecimal credit, int accountType){
        JsonResult result = new JsonResult<>(YES);
        int userType = userMapper.GetUserTypeByUsername(username);
        BigDecimal balance = new BigDecimal(0);
        //根据userType和accountType来判断，直接在mapper层加上。
        switch (userType){
            case admin:
                if(accountType == profitAccount) {
                    userMapper.RechargeProfitAccountByUsername(username, credit);
                    balance = userMapper.GetProfitAccountByUsername(username);
                }
                else if (accountType == intermediaryAccount) {
                    userMapper.RechargeIntermediaryAccountByUsername(username, credit);
                    balance = userMapper.GetIntermediaryAccountByUsername(username);
                }
                break;
            case merchant:
                if(accountType == privateAccount) {
                    userMapper.RechargePrivateAccountByUsername(username, credit);
                    balance = userMapper.GetPrivateAccountByUsername(username);
                }
                else if (accountType == shopAccount) {
                    userMapper.RechargeShopAccountByUsername(username, credit);
                    balance = userMapper.GetShopAccountByUsername(username);
                }
                break;
            case buyer: {
                userMapper.RechargePrivateAccountByUsername(username, credit);
                balance = userMapper.GetPrivateAccountByUsername(username);
            }
                break;
            default:
                result.setState(NO);
                System.out.println("账户类型异常");
        }
        result.setData(balance);
        return result;
    }


    @Override
    public JsonResult getUserTransactions(String username){
        JsonResult result = new JsonResult<>(YES);
        List<Transaction> allTransactionResult = transactionMapper.getAllTransactionsByusername(username);
        result.setData(allTransactionResult);
        return result;
    }



    @Override
    public JsonResult getProfitAccount(){
        BigDecimal profitAccount = userMapper.GetProfitAccount();
        JsonResult result = new JsonResult<>(YES,profitAccount);
        return  result;
    }

    @Override
    public JsonResult getIntermediaryAccount(){
        BigDecimal intermediaryAccount = userMapper.GetIntermediaryAccount();
        JsonResult result = new JsonResult<>(YES,intermediaryAccount);
        return result;
    }

    @Override
    public JsonResult getShopAccount(String username){
        BigDecimal shopAccount = userMapper.GetShopAccountByUsername(username);
        JsonResult result = new JsonResult<>(YES, shopAccount);
        return result;
    }
    @Override
    public void deleteUserAvatar(String username){
        userMapper.AvatarDelete(username);
    }




}
