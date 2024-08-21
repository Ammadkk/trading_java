package com.market.trading.repository;

import com.market.trading.Model.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDetailsRep extends JpaRepository<PaymentDetails, Long> {

    public PaymentDetails findByUserId(Long userId);


}
