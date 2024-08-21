package com.market.trading.service;

import com.market.trading.Model.ForgotPasswordToken;
import com.market.trading.Model.User;
import com.market.trading.domain.VerificationType;

public interface ForgotPasswordService {

    ForgotPasswordToken createToken(User user,
                                    String id,
                                    String otp,
                                    VerificationType verificationType,
                                    String sendTo);

    ForgotPasswordToken findById(String id);

    ForgotPasswordToken findByUser(Long userId);

    void deleteToken(ForgotPasswordToken token);


}
