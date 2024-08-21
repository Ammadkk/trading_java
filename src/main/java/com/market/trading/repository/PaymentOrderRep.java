package com.market.trading.repository;

import com.market.trading.Model.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentOrderRep extends JpaRepository<PaymentOrder, Long> {
}
