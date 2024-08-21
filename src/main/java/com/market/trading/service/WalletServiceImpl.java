package com.market.trading.service;


import com.market.trading.Model.Order;
import com.market.trading.Model.User;
import com.market.trading.Model.Wallet;
import com.market.trading.domain.OrderType;
import com.market.trading.repository.WalletRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service

public class WalletServiceImpl implements WalletService {


    @Autowired
    private WalletRep walletRep;

    @Override
    public Wallet getUserWallet(User user) {
        Wallet wallet = walletRep.findByUserId(user.getId());
        if (wallet == null) {
            wallet = new Wallet();
            wallet.setUser(user);
            walletRep.save(wallet);
        }
        return wallet;
    }

    @Override
    public Wallet addBalance(Wallet wallet, Long money) {

        BigDecimal balance = wallet.getBalance();
        BigDecimal newbalance = balance.add(BigDecimal.valueOf(money));

        wallet.setBalance(newbalance);

        return walletRep.save(wallet);
    }

    @Override
    public Wallet findWalletById(Long id) throws Exception {
        Optional<Wallet> wallet = walletRep.findById(id);
        if (wallet.isPresent()) {
            return wallet.get();
        }
        throw new Exception("wallet not found");
    }


    @Override
    public Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, Long amount) throws Exception {

       Wallet senderWallet = getUserWallet(sender);
       if (senderWallet.getBalance().compareTo(BigDecimal.valueOf(amount)) <0){
           throw new Exception("Insufficient balance");
       }
       BigDecimal senderBalance = senderWallet.getBalance().subtract(BigDecimal.valueOf(amount));
       senderWallet.setBalance(senderBalance);

       walletRep.save(senderWallet);

       BigDecimal receiverBalance = receiverWallet.getBalance().add(BigDecimal.valueOf(amount));
       receiverWallet.setBalance(receiverBalance);
       walletRep.save(receiverWallet);

        return senderWallet;
    }

    @Override
    public Wallet payOrderPayment(Order order, User user) throws Exception {
        Wallet wallet = getUserWallet(user);

        if (order.getOrderType().equals(OrderType.BUY)){
            BigDecimal newBalance = wallet.getBalance().subtract(order.getPrice());
            if (newBalance.compareTo(order.getPrice()) <0){
                throw new Exception("Insufficient funds for transaction");
            }
            wallet.setBalance(newBalance);
        }
        else {
            BigDecimal newBalance = wallet.getBalance().add(order.getPrice());
            wallet.setBalance(newBalance);
        }


        walletRep.save(wallet);

        return wallet;
    }

}
