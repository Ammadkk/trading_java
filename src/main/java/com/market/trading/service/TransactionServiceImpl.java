//package com.market.trading.service;
//
//import com.market.trading.Model.Wallet;
//import com.market.trading.Model.WalletTransaction;
//import com.market.trading.domain.WalletTransactionType;
//import com.market.trading.repository.WalletTransactionRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@Service
//public class TransactionServiceImpl implements TransactionService {
//
//
//    @Autowired
//    private WalletTransactionRepository walletTransactionRepository;
//
//
//    @Override
//    public List<WalletTransaction> getTransactionsByWallet(Wallet wallet) {
//        return walletTransactionRepository.findByWallet(wallet);
//    }
//
//    @Override
//    public WalletTransaction createTransaction(Wallet wallet, WalletTransactionType type,
//                                               String transferId, String purpose,
//                                               Long amount) {
//        WalletTransaction transaction = new WalletTransaction();
//        transaction.setWallet(wallet);
//        transaction.setType(type);
////        transaction.setDate(date);
//        transaction.setTransferId(transferId);
//        transaction.setPurpose(purpose);
//        transaction.setAmount(amount);
//
//        return walletTransactionRepository.save(transaction);
//    }
//}

package com.market.trading.service;

import com.market.trading.Model.Wallet;
import com.market.trading.Model.WalletTransaction;
import com.market.trading.domain.WalletTransactionType;
import com.market.trading.repository.WalletTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private WalletTransactionRepository walletTransactionRepository;

    @Override
    public List<WalletTransaction> getTransactionsByWallet(Wallet wallet) {
        // Fetch transactions by wallet ID
        return walletTransactionRepository.findByWallet(wallet);
    }

    @Override
    public WalletTransaction createTransaction(Wallet wallet, WalletTransactionType type,
                                               String transferId, String purpose, Long amount) {
        // Create and save a new transaction
        WalletTransaction transaction = new WalletTransaction();
        transaction.setWallet(wallet);              // Link transaction to wallet
        transaction.setType(type);                  // Set the transaction type (deposit, withdrawal, etc.)
        transaction.setTransferId(transferId);      // Set the transfer ID if applicable
        transaction.setPurpose(purpose);            // Set the purpose of the transaction
        transaction.setAmount(amount);              // Set the transaction amount
        transaction.setDate(LocalDate.now());       // Set the current date

        // Save the transaction to the repository (persist it in the database)
        return walletTransactionRepository.save(transaction);
    }
}

