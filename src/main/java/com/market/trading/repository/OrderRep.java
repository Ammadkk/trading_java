package com.market.trading.repository;

import com.market.trading.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderRep extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);


}
