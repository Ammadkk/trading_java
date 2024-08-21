package com.market.trading.repository;

import com.market.trading.Model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRep extends JpaRepository<Coin, String> {}
