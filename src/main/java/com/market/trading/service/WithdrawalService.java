package com.market.trading.service;


import com.market.trading.Model.User;
import com.market.trading.Model.Withdrawal;

import java.util.List;

public interface WithdrawalService {


    Withdrawal requestWithdrawal(Long amount, User user);

    Withdrawal proceedWithdrawal(Long WithdrawalId, boolean accept) throws Exception;

    List<Withdrawal> getUsersWithdrawalHistory(User user);

    List<Withdrawal> getAllWithdrawalRequest();

}
