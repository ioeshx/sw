package com.example.online_shopping_website.service;

import com.example.online_shopping_website.util.JsonResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static com.example.online_shopping_website.entity.constant.AccountType.*;
import static com.example.online_shopping_website.entity.constant.TransactionType.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TransactionServiceTest {
    @Autowired
    private TransactionService transactionService;

    @Test
    public void InsertTransaction(){
        transactionService.InsertTransaction("jason", "ivy", chargeToPrivateAccount, privateAccount,
                                             new BigDecimal(100));
    }
}
