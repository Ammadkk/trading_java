package com.market.trading.repository;

import com.market.trading.Model.ForgotPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ForgotPasswordRep extends JpaRepository<ForgotPasswordToken, String> {

    ForgotPasswordToken findByUserId(Long userId);


}
