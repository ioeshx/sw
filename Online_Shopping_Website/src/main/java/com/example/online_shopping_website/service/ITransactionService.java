package com.example.online_shopping_website.service;

import com.example.online_shopping_website.util.JsonResult;

public interface ITransactionService {

    JsonResult getUserTransactions(String username, int accountType);
    JsonResult getUserProfit(String username, int periodType);
    JsonResult getUserTransactionsByPeriod(String username, int accountType, int periodType);
}
