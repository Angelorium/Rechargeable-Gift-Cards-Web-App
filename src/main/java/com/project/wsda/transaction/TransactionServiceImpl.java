package com.project.wsda.transaction;

import com.project.wsda.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Override
    public void saveTransaction(TransactionDto transactionDto){
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDto.getAmount());
        transaction.setShop(userRepository.findByUsername(transactionDto.getShopUsername()));
        transaction.setCardId(transactionDto.getCardId());
        transaction.setTimestamp(transactionDto.getTimestamp());
        transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> findTransactionsByUser(User user){
        String username = user.getUsername();
        return userRepository.findByUsername(username).getTransactions();
    }
}
