package com.market.trading.service;


import com.market.trading.Model.Coin;

import java.util.List;

public interface CoinService {

    List<Coin> getCoinList(int page) throws Exception;

    String getMarketChart(String coinID, int days) throws Exception;

//use coin api to retrieve details
    String getCoinDetails(String coinId) throws Exception;

//use id from database to get coin det
    Coin findById(String coinId) throws Exception;

    String searchCoin(String keyword) throws Exception;

    String getTop50CoinsByMarketCapRank() throws Exception;

    String getTradingCoins() throws Exception;


}
