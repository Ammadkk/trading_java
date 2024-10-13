//package com.market.trading.controller;
//
//import com.market.trading.Model.User;
//import com.market.trading.Model.Wallet;
//import com.market.trading.Model.WalletTransaction;
//import com.market.trading.service.TransactionService;
//import com.market.trading.service.UserService;
//import com.market.trading.service.WalletService;
//import com.market.trading.service.WalletServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//public class TransactionC {
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private TransactionService transactionService;
//    @Autowired
//    private WalletService walletService;
//
//    @GetMapping("/api/transactions")
//    public ResponseEntity<List<WalletTransaction>> getUserWallet(
//            @RequestHeader("Authorization") String jwt) throws Exception{
//        User user = userService.findUserProfileByJwt(jwt);
//
//
//        Wallet wallet = walletService.getUserWallet(user);
//
//        List<WalletTransaction> transactionList = transactionService.getTransactionsByWallet(wallet);
//        System.out.println("Transactions returned: " + transactionList.size());
//
//        return new ResponseEntity<>(transactionList, HttpStatus.ACCEPTED);
//    }
//
//}

package com.market.trading.controller;

import com.market.trading.Model.User;
import com.market.trading.Model.Wallet;
import com.market.trading.Model.WalletTransaction;
import com.market.trading.service.TransactionService;
import com.market.trading.service.UserService;
import com.market.trading.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionC {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private WalletService walletService;

    @GetMapping("/api/transactions")
    public ResponseEntity<List<WalletTransaction>> getUserWallet(
            @RequestHeader("Authorization") String jwt) throws Exception{

        // Fetch user based on JWT
        User user = userService.findUserProfileByJwt(jwt);

        // Get the user's wallet
        Wallet wallet = walletService.getUserWallet(user);

        // Log the wallet ID for debugging
        System.out.println("Wallet ID: " + wallet.getId());

        // Fetch the transactions associated with this wallet
        List<WalletTransaction> transactionList = transactionService.getTransactionsByWallet(wallet);

        // Log the size of the transaction list to verify transactions returned
        System.out.println("Transactions returned: " + transactionList.size());

        // Return the list of transactions
        return new ResponseEntity<>(transactionList, HttpStatus.ACCEPTED);
    }

}

