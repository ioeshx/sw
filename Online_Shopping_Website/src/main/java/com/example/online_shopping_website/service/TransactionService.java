package com.example.online_shopping_website.service;

import com.example.online_shopping_website.entity.Transaction;
import com.example.online_shopping_website.mapper.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class TransactionService {
    @Autowired
    private TransactionMapper transactionMapper;
    public void InsertTransaction(String outPart, String inPart, int outAccountType, int inAccountType,
                                  int transactionType,  BigDecimal amount){
        Date transactionDate = new Date();
        Transaction transaction = new Transaction(transactionDate, transactionType, outPart, inPart, outAccountType, inAccountType, amount );
        transactionMapper.InsertTransaction(transaction);
        return ;
    }
}
