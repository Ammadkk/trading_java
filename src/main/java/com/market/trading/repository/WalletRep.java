package com.market.trading.repository;

import com.market.trading.Model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRep extends JpaRepository<Wallet, Long> {

    Wallet findByUserId(Long userId);

}
