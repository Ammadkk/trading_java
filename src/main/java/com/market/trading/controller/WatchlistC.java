package com.market.trading.controller;

import com.market.trading.Model.Coin;
import com.market.trading.Model.User;
import com.market.trading.Model.Watchlist;
import com.market.trading.service.CoinService;
import com.market.trading.service.UserService;
import com.market.trading.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/watchlist")

public class WatchlistC {


    @Autowired
    private WatchlistService watchlistService;
    @Autowired
    private UserService userService;
    @Autowired
    private CoinService coinService;


    @GetMapping("/user")
    public ResponseEntity<Watchlist> getUserWatchlist(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Watchlist watchlist = watchlistService.findUserWatchlist(user.getId());
        return ResponseEntity.ok(watchlist);
    }

//    @PostMapping("/create")
//    public ResponseEntity<Watchlist> createWatchlist(
//            @RequestHeader("Authorization") String jwt
//    ) throws Exception {
//        User user = userService.findUserProfileByJwt(jwt);
//        Watchlist watchlist = watchlistService.createWatchlist(user);
//        return ResponseEntity.ok(watchlist);
//    }


    @GetMapping("/{watchlistId}")
    public ResponseEntity<Watchlist> getWatchListById(
            @PathVariable Long watchlistId
    ) throws Exception {
        Watchlist watchlist = watchlistService.findById(watchlistId);
        return ResponseEntity.ok(watchlist);
    }


    @PatchMapping("/add/coin/{coinId}")
    public ResponseEntity<Coin> addItemTowatchlist(
            @RequestHeader("Authorization") String jwt,
            @PathVariable String coinId
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Coin coin = coinService.findById(coinId);
        Coin addedCoin = watchlistService.addItemToWatchlist(coin, user);
        return ResponseEntity.ok(addedCoin);
    }



}
