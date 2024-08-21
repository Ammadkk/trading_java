package com.market.trading.repository;

import com.market.trading.Model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRep extends JpaRepository<VerificationCode, Long> {
    public VerificationCode findByUserId(Long userId);
}
