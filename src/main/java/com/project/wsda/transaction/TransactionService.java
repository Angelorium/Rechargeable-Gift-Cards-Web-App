package com.project.wsda.transaction;

import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface TransactionService {

    void saveTransaction(TransactionDto transactionDto);

    List<Transaction> findTransactionsByUser(User user);
}
