package com.market.trading.service;

import com.market.trading.Model.TwoFactorOTP;
import com.market.trading.Model.User;

public interface TwoFactorOTPService {

    TwoFactorOTP createTwoFactorOTP(User user, String otp, String jwt);

    TwoFactorOTP findByUser(Long userId);

    TwoFactorOTP findById(String id);

    boolean verifyTwoFactorOTP(TwoFactorOTP twoFactorOTP,String otp);

    void deleteTwoFactorOTP(TwoFactorOTP twoFactorOTP);

}

