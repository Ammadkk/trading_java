package com.market.trading.service;


import com.market.trading.Model.User;
import com.market.trading.Model.Withdrawal;
import com.market.trading.domain.WithdrawalStatus;
import com.market.trading.repository.WithdrawalRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class WithdrawalServiceImpl implements WithdrawalService{

    @Autowired
    private WithdrawalRep withdrawalRep;

    @Autowired
    private UserService userService;


    @Override
    public Withdrawal requestWithdrawal(Long amount, User user) {
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setAmount(amount);
        withdrawal.setUser(user);
        withdrawal.setStatus(WithdrawalStatus.PENDING);

        return withdrawalRep.save(withdrawal);
    }


    @Override
    public Withdrawal proceedWithdrawal(Long WithdrawalId, boolean accept) throws Exception {
        Optional<Withdrawal> withdrawal = withdrawalRep.findById(WithdrawalId);
        if (withdrawal.isEmpty()){
            throw new Exception("Withdrawal not found");
        }
        Withdrawal withdrawal1 = withdrawal.get();
        withdrawal1.setDate(LocalDateTime.now());
        if (accept){
            withdrawal1.setStatus(WithdrawalStatus.SUCCESS);
        }
        else {
            withdrawal1.setStatus(WithdrawalStatus.PENDING);
        }

        return withdrawalRep.save(withdrawal1);
    }

    @Override
    public List<Withdrawal> getUsersWithdrawalHistory(User user) {
        return withdrawalRep.findByUserId(user.getId());
    }

    @Override
    public List<Withdrawal> getAllWithdrawalRequest() {
        return withdrawalRep.findAll();
    }
}
