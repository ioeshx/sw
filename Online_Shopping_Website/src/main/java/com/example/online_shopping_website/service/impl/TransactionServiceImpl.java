package com.example.online_shopping_website.service.impl;

import com.example.online_shopping_website.entity.Transaction;
import com.example.online_shopping_website.mapper.TransactionMapper;
import com.example.online_shopping_website.mapper.UserMapper;
import com.example.online_shopping_website.service.ITransactionService;
import com.example.online_shopping_website.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.example.online_shopping_website.entity.constant.PeriodType.Month;
import static com.example.online_shopping_website.entity.constant.PeriodType.Week;
import static com.example.online_shopping_website.entity.constant.UserType.*;
import static com.example.online_shopping_website.entity.constant.UserType.admin;
import static javax.security.auth.callback.ConfirmationCallback.NO;
import static javax.security.auth.callback.ConfirmationCallback.YES;

@Service
public class TransactionServiceImpl implements ITransactionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TransactionMapper transactionMapper;

    @Override
    public JsonResult getUserTransactions(String username, int accountType){
        //判断用户类型来选择SQL语句
        List<Transaction> allTransactions = new ArrayList<>();
        int userType = userMapper.GetUserTypeByUsername(username);
        if(userType != admin)
            allTransactions = transactionMapper.GetTransactionsByUsername(username, accountType);
        else
            allTransactions = transactionMapper.GetAdminTransactions(accountType);

        if(!allTransactions.isEmpty())
            return new JsonResult<>(YES,"成功获取流水记录", allTransactions);
        else
            return new JsonResult<>(NO, "未找到流水记录");
    }

    @Override
    public JsonResult getUserProfit(String username, int periodType){
        int userType = userMapper.GetUserTypeByUsername(username);
        JsonResult result = new JsonResult<>(YES,"成功！");
        BigDecimal profit = new BigDecimal(0);
        List<BigDecimal> profits = new ArrayList<>();

        if(userType == admin){  //管理员计算流水利润，利润只算订单的佣金和商店注册资金
            if(periodType == Week)     //近一周利润
                profits= transactionMapper.GetAdminProfitForWeek();
            else if(periodType == Month)  //近一个月利润
                profits = transactionMapper.GetAdminProfitForMonth();
        }else if(userType == merchant){ //商家计算流水利润，利润只算订单支付的金额
            if(periodType == Week)     //近一周利润
                profits= transactionMapper.GetShopProfitForWeek(username);
            else if(periodType == Month)  //近一个月利润
                profits = transactionMapper.GetShopProfitForMonth(username);
        }else if(userType == buyer){
            return new JsonResult<>(NO,"普通买家无法计算流水利润");
        }

        for(BigDecimal p : profits)
            profit = profit.add(p);
        result.setData(profit);
        return result;
    }

    @Override
    public JsonResult getUserTransactionsByPeriod(String username, int accountType, int periodType){
        List<Transaction> allTransactions = new ArrayList<>();
        int userType = userMapper.GetUserTypeByUsername(username);
        if(userType != admin){
            if(periodType == Week)
                allTransactions = transactionMapper.GetTransactionsByUsernameForWeek(username, accountType);
            else if(periodType == Month)
                allTransactions = transactionMapper.GetTransactionsByUsernameForMonth(username, accountType);
        } else{
            if(periodType == Week)
                allTransactions = transactionMapper.GetAdminTransactionsForWeek();
            else if(periodType == Month)
                allTransactions = transactionMapper.GetAdminTransactionsForMonth();
        }

        if(!allTransactions.isEmpty())
            return new JsonResult<>(YES,"成功获取流水记录", allTransactions);
        else
            return new JsonResult<>(NO, "未找到流水记录");
    }
}
