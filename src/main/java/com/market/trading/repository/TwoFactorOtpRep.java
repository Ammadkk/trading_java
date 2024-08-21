package com.market.trading.repository;

import com.market.trading.Model.TwoFactorOTP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwoFactorOtpRep extends JpaRepository<TwoFactorOTP, String> {
    TwoFactorOTP findByUserId(Long userId);

}
