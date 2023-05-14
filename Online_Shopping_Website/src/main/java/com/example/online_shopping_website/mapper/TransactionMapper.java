package com.example.online_shopping_website.mapper;


import com.example.online_shopping_website.entity.Transaction;

import java.util.List;

public interface TransactionMapper {

    List<Transaction>  getAllTransactionsByusername(String username);

    void InsertTransaction(Transaction transaction);
}
