package com.example.online_shopping_website.controller;

import com.example.online_shopping_website.service.IUserService;
import com.example.online_shopping_website.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TransactionController {

    @Autowired
    private IUserService userService;

    @RequestMapping("/api/getUserTransactions")
    public JsonResult getUserTransactions(@RequestBody Map<String,Object> map){
        String username = (String)map.get("username");
        return userService.getUserTransactions(username);
    }
}
