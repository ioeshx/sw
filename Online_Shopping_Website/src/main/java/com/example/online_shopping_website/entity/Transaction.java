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
    private String outPart;         //交易的转出方，存用户名
    private String inPart;          //交易的转入方，存用户名
    private int outAccountType; //转出方的账户类型
    private int inAccountType;  //转入方的账户类型
    private BigDecimal amount;  //金额数量

    public Transaction(Date transactionDate, int transactionType, String outPart, String inPart, int outAccountType, int inAccountType, BigDecimal amount) {
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.outPart = outPart;
        this.inPart = inPart;
        this.outAccountType = outAccountType;
        this.inAccountType = inAccountType;
        this.amount = amount;
    }
}
