package com.example.online_shopping_website.mapper;


import com.example.online_shopping_website.entity.Transaction;

import java.util.List;

public interface TransactionMapper {

    List<Transaction>  getTransactionsByUsername(String username, int accountType);

    List<Transaction> GetAdminTransactions(int accountType);

    void InsertTransaction(Transaction transaction);
}
