package com.example.online_shopping_website.service.impl;

import com.example.online_shopping_website.entity.User;

import java.util.regex.*;
public class InfoVerification {

    public static Boolean checkAllInfoValid(User user){
        Boolean result = Boolean.TRUE;
        if(!InfoVerification.isUsernameValid(user.getUsername())
                ||!InfoVerification.isPhoneValid(user.getPhone())
                ||!InfoVerification.isIdnumValid(user.getIdnum())
                ||!InfoVerification.isEmailValid(user.getEmail())
          ) result = Boolean.FALSE;

        return result;
    }

    public static Boolean isUsernameValid(String username){
        Boolean result = Boolean.FALSE;
        if(username.matches("^[a-zA-Z0-9_]{3,10}$")){
            result = Boolean.TRUE;
        }
        return result;
    }

    /*只检查了手机号是否为数字，因为格式还不固定
    * */
    public static Boolean isPhoneValid(String phone){
        Boolean result = Boolean.FALSE;
        if(phone.matches("^[0-9]$")){
            result = Boolean.TRUE;
        }
        return result;
    }

    public static Boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static Boolean isIdnumValid(String idnum) {
        Pattern pattern = Pattern.compile("^[0-9]{18}$");
        Matcher matcher = pattern.matcher(idnum);
        return matcher.matches();
    }


}
