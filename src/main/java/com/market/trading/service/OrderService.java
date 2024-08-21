package com.market.trading.service;


import com.market.trading.Model.Coin;
import com.market.trading.Model.Order;
import com.market.trading.Model.OrderItem;
import com.market.trading.Model.User;
import com.market.trading.domain.OrderType;

import java.util.List;

public interface OrderService {


    Order createOrder(User user, OrderItem orderItem, OrderType orderType);

    Order getOrderById(Long orderId) throws Exception;

    List<Order> getAllOrdersOfUsers(Long userId,OrderType orderType,String assetSymbol);

    Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception;



}
