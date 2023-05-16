package com.example.online_shopping_website.mapper;


import com.example.online_shopping_website.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionMapper {

    List<Transaction>  GetTransactionsByUsername(String username, int accountType);

    List<Transaction> GetAdminTransactions(int accountType);

    void InsertTransaction(Transaction transaction);

    List<BigDecimal> GetAdminProfitForWeek();
    List<BigDecimal> GetAdminProfitForMonth();
    List<BigDecimal> GetShopProfitForWeek(String username);
    List<BigDecimal> GetShopProfitForMonth(String username);

    List<Transaction> GetTransactionsByUsernameForWeek(String username, int accountType);
    List<Transaction> GetTransactionsByUsernameForMonth(String username, int accountType);
    List<Transaction> GetAdminTransactionsForWeek();
    List<Transaction> GetAdminTransactionsForMonth();
}
