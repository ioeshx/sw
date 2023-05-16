package com.example.online_shopping_website.controller;

import com.example.online_shopping_website.service.ITransactionService;
import com.example.online_shopping_website.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TransactionController {
    @Autowired
    private ITransactionService transactionService;

    @RequestMapping("/api/getUserTransactions")
    public JsonResult getUserTransactions(@RequestBody Map<String,Object> map){
        String username = (String)map.get("username");
        int accountType = (int)map.get("accountType");
        return transactionService.getUserTransactions(username, accountType);
    }

    @RequestMapping("/api/getUserProfit")
    public JsonResult getUserProfit(@RequestBody Map<String,Object> map){
        String username = (String)map.get("username");
        int periodType = (int)map.get("periodType");
        return transactionService.getUserProfit(username, periodType);
    }

    @RequestMapping("/api/getUserTransactionsByPeriod")
    public JsonResult getUserTransactionsByPeriod(@RequestBody Map<String,Object> map){
        String username = (String)map.get("username");
        int accountType = (int)map.get("accountType");
        int periodType = (int)map.get("periodType");
        return transactionService.getUserTransactionsByPeriod(username, accountType,periodType);
    }
}
