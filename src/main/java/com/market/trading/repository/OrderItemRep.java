package com.market.trading.repository;

import com.market.trading.Model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRep extends JpaRepository<OrderItem, Long> {
}
