package com.market.trading.request;


import com.market.trading.domain.OrderType;
import lombok.Data;

@Data

public class CreateOrderRequest {
    private String coinId;

    private double quantity;

    private OrderType orderType;
}
