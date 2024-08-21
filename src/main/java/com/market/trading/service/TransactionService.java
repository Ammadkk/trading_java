package com.market.trading.service;

import com.market.trading.Model.Wallet;
import com.market.trading.Model.WalletTransaction;
import com.market.trading.domain.WalletTransactionType;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    List<WalletTransaction> getTransactionsByWallet(Wallet wallet);
    WalletTransaction createTransaction(Wallet wallet, WalletTransactionType type,
                                        String transferId, String purpose, Long amount);
}
