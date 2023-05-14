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
    private int transactionId;
    private Date transactionDate;
    private int transactionType;
    private int accountType;
    private String fromWho;
    private String toWho;
    private BigDecimal amount;
    private BigDecimal balance;

}
