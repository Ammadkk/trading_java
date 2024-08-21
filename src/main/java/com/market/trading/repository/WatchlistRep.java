package com.market.trading.repository;

import com.market.trading.Model.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchlistRep extends JpaRepository<Watchlist, Long> {

    Watchlist findByUserId(Long userId);

}
