package com.market.trading.controller;


import com.market.trading.Model.Coin;
import com.market.trading.Model.Order;
import com.market.trading.Model.User;
import com.market.trading.domain.OrderType;
import com.market.trading.request.CreateOrderRequest;
import com.market.trading.service.CoinService;
import com.market.trading.service.OrderService;
import com.market.trading.service.UserService;
import com.market.trading.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")

public class OrderC {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private CoinService coinService;

//    @Autowired
//    private WalletService walletService;

    @PostMapping("/pay")
    public ResponseEntity<Order> payOrderPayment(
            @RequestHeader("Authorization") String jwt,
            @RequestBody CreateOrderRequest req)
        throws Exception {

        if (req.getCoinId() == null) {
            throw new IllegalArgumentException("Coin ID must not be null");
        }

        User user = userService.findUserProfileByJwt(jwt);
        Coin coin = coinService.findById(req.getCoinId());

        Order order = orderService.processOrder(coin,req.getQuantity(),req.getOrderType(),user);

        return ResponseEntity.ok(order);
    }


    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long orderId
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Order order = orderService.getOrderById(orderId);

        if (order.getUser().getId().equals(user.getId())) {
            return ResponseEntity.ok(order);
        } else {
            throw new Exception("You don't have access");
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Order>> getAllOrders(
            @RequestHeader("Authorization") String jwt,
            @RequestParam(required = false) OrderType order_type,
            @RequestParam(required = false) String asset_symbol
    ) throws Exception {

        Long userId= userService.findUserProfileByJwt(jwt).getId();
        List<Order> userOrders = orderService.getAllOrdersOfUsers(userId,order_type,asset_symbol);

        return ResponseEntity.ok(userOrders);

    }




}
