package com.market.trading.repository;


import com.market.trading.Model.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WithdrawalRep extends JpaRepository<Withdrawal, Long> {

    List<Withdrawal> findByUserId(Long userId);


}
