package com.market.trading.service;


import com.market.trading.Model.*;
import com.market.trading.domain.OrderStatus;
import com.market.trading.domain.OrderType;
import com.market.trading.repository.OrderItemRep;
import com.market.trading.repository.OrderRep;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;



@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    private OrderRep orderRep;

    @Autowired
    private OrderItemRep orderItemRep;

    @Autowired
    private WalletService walletService;

    @Autowired
    private AssetService assetService;


    @Override
    public Order createOrder(User user, OrderItem orderItem, OrderType orderType) {
        double price = orderItem.getCoin().getCurrentPrice()*orderItem.getQuantity();

        Order order = new Order();
        order.setUser(user);
        order.setOrderItem(orderItem);
        order.setOrderType(orderType);
        order.setPrice(BigDecimal.valueOf(price));
        order.setTimestamp(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        return orderRep.save(order);

    }

    @Override
    public Order getOrderById(Long orderId) throws Exception {
        return orderRep.findById(orderId).orElseThrow(()-> new Exception("Order not found"));
    }

    @Override
    public List<Order> getAllOrdersOfUsers(Long userId, OrderType orderType, String assetSymbol) {
        return orderRep.findByUserId(userId);
    }

    private OrderItem createOrderItem(Coin coin,double quantity,double buyPrice,double sellPrice){
        OrderItem orderItem = new OrderItem();
        orderItem.setCoin(coin);
        orderItem.setQuantity(quantity);
        orderItem.setBuyPrice(buyPrice);
        orderItem.setSellPrice(sellPrice);
        return orderItemRep.save(orderItem);
    }

    @Transactional
    public Order buyAssets(Coin coin,double quantity,User user) throws Exception {
        if (quantity<=0){
            throw new Exception("quantity should be >0");
        }
        double buyPrice = coin.getCurrentPrice();

        OrderItem orderItem = createOrderItem(coin,quantity,buyPrice,0);

        Order order = createOrder(user, orderItem, OrderType.BUY);

        orderItem.setOrder(order);

        walletService.payOrderPayment(order, user);

        order.setStatus(OrderStatus.SUCCESS);
        order.setOrderType(OrderType.BUY);
        Order savedOrder = orderRep.save(order);

        //create asset

        Asset oldAsset = assetService.findAssetByUserIdAndCoinId(
                order.getUser().getId(),orderItem.getCoin().getId());

        if (oldAsset == null) {
            assetService.createAsset(user,orderItem.getCoin(),orderItem.getQuantity());
        }
        else {
            assetService.updateAsset(oldAsset.getId(),quantity);
        }


        return savedOrder;
    }



    @Transactional
    public Order sellAssets(Coin coin,double quantity,User user) throws Exception {
        if (quantity <= 0) {
            throw new Exception("quantity should be >0");
        }
        double sellPrice = coin.getCurrentPrice();

        Asset assetToSell = assetService.findAssetByUserIdAndCoinId(
                user.getId(),
                coin.getId());
        double buyPrice = assetToSell.getBuyPrice();


        if (assetToSell != null) {
            OrderItem orderItem = createOrderItem(coin,
                    quantity,
                    buyPrice,
                    sellPrice);


            Order order = createOrder(user, orderItem, OrderType.SELL);
            orderItem.setOrder(order);

            if (assetToSell.getQuantity() >= quantity) {
                order.setStatus(OrderStatus.SUCCESS);
                order.setOrderType(OrderType.SELL);
                Order savedOrder = orderRep.save(order);

                walletService.payOrderPayment(order, user);

                Asset updatedAsset = assetService.updateAsset(assetToSell.getId(),-quantity);


                if (updatedAsset.getQuantity() * coin.getCurrentPrice() <= 1) {
                    assetService.deleteAsset(updatedAsset.getId());
                }
                return savedOrder;


            }
            throw new Exception("Insufficient quantity to sell");

        }throw new Exception("Asset not found");

    }








//    public Order sellAssets(Coin coin,double quantity,User user) throws Exception {
//        if (quantity<=0){
//            throw new Exception("quantity should be >0");
//        }
//
//        double sellPrice = coin.getCurrentPrice();
//
//        Asset assetToSell = assetService.findAssetByUserIdAndCoinId(user.getId(), coin.getId());
//
//        if (assetToSell != null) {
//            OrderItem orderItem = createOrderItem(coin,quantity,buyPrice,sellPrice);
//        }
//
//        double buyPrice = assetToSell.getCurrentPrice();
//
//        OrderItem orderItem = createOrderItem(coin,quantity,buyPrice,sellPrice);
//        Order order = createOrder(user, orderItem, OrderType.SELL);
//        orderItem.setOrder(order);
//
//        if (assetToSell.getQuantity()>=quantity){
//            walletService.payOrderPayment(order, user);
//
//
//            Asset updatedAsset=assetService.updateAsset(assetToSell.getId(),-quantity);
//            if (updatedAsset.getQuantity()*coin.getCurrentPrice()<=1){
//                assetService.deleteAsset(updatedAsset.getId);
//            }
//            return savedOrder;
//        }
//        throw new Exception("Insufficient quantity to sell");
//
//
//        order.setStatus(OrderStatus.SUCCESS);
//        order.setOrderType(OrderType.SELL);
//        Order savedOrder = orderRep.save(order);
//
//
//        return savedOrder;
//    }



    @Override
    @Transactional
    public Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception {

        if (orderType.equals(OrderType.BUY)){
            return buyAssets(coin,quantity,user);
        }
        else if (orderType.equals(OrderType.SELL)) {
            return sellAssets(coin,quantity,user);
        }

        throw new Exception("Invalid order type");
    }
}
