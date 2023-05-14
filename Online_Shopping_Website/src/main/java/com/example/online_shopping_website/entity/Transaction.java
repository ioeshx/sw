package com.example.online_shopping_website.entity;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private int transactionId;      //交易ID
    private Date transactionDate;   //日期
    private int transactionType;    //交易类型
    private int accountType;        //什么类型的账户对应的流水 在AccountTye类中
    private String host;            //交易的主体，存用户名
    private String counterparty;    //交易的另一方，存用户名
    private BigDecimal amount;      //金额数量

    public Transaction(Date transactionDate, int transactionType, int accountType, String host, String counterparty, BigDecimal amount) {
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.accountType = accountType;
        this.host = host;
        this.counterparty = counterparty;
        this.amount = amount;
    }
}
