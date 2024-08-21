package com.market.trading.service;

import com.market.trading.Model.Coin;
import com.market.trading.Model.User;
import com.market.trading.Model.Watchlist;
import com.market.trading.repository.WatchlistRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class WatchlistServiceImpl implements WatchlistService {

    @Autowired
    private WatchlistRep watchlistRep;



    @Override
    public Watchlist findUserWatchlist(Long userId) throws Exception {
        Watchlist watchlist = watchlistRep.findByUserId(userId);
        if (watchlist==null){
            throw new Exception("Watchlist not found");
        }

        return watchlist;
    }



    @Override
    public Watchlist createWatchlist(User user) {
        Watchlist watchlist = new Watchlist();
        watchlist.setUser(user);

        return watchlistRep.save(watchlist);
    }


    @Override
    public Watchlist findById(Long id) throws Exception {
        Optional<Watchlist> watchlistOptional = watchlistRep.findById(id);
        if (watchlistOptional.isEmpty()){
            throw new Exception("Watchlist not found");

        }
        return watchlistOptional.get();
    }


    @Override
    public Coin addItemToWatchlist(Coin coin, User user) {
        Watchlist watchlist = watchlistRep.findByUserId(user.getId());

        if (watchlist.getCoins().contains(coin)){
            watchlist.getCoins().remove(coin);
        }

        else watchlist.getCoins().add(coin);
        watchlistRep.save(watchlist);
        return coin;

    }
}
