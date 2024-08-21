package com.market.trading.service;


import com.market.trading.Model.Coin;
import com.market.trading.Model.User;
import com.market.trading.Model.Watchlist;

public interface WatchlistService {

    Watchlist findUserWatchlist(Long userId) throws Exception;

    Watchlist createWatchlist(User user);

    Watchlist findById(Long id) throws Exception;

    Coin addItemToWatchlist(Coin coin, User user);

}
