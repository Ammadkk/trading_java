package com.market.trading.service;

import com.market.trading.Model.TwoFactorOTP;
import com.market.trading.Model.User;
import com.market.trading.repository.TwoFactorOtpRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;



@Service
public class TwoFactorOtpServiceImpl implements TwoFactorOTPService{

    @Autowired
    private TwoFactorOtpRep twoFactorOtpRep;


    @Override
    public TwoFactorOTP createTwoFactorOTP(User user, String otp, String jwt) {


        UUID uuid = UUID.randomUUID();

        String id = uuid.toString();

        TwoFactorOTP twoFactorOTP = new TwoFactorOTP();
        twoFactorOTP.setOtp(otp);
        twoFactorOTP.setJwt(jwt);
        twoFactorOTP.setId(id);
        twoFactorOTP.setUser(user);


        return twoFactorOtpRep.save(twoFactorOTP);
    }

    @Override
    public TwoFactorOTP findByUser(Long userId) {
        return twoFactorOtpRep.findByUserId(userId);
    }

    @Override
    public TwoFactorOTP findById(String id) {
        Optional<TwoFactorOTP> opt= twoFactorOtpRep.findById(id);

        return opt.orElse(null);
    }

    @Override
    public boolean verifyTwoFactorOTP(TwoFactorOTP twoFactorOTP, String otp) {
        return twoFactorOTP.getOtp().equals(otp);
    }

    @Override
    public void deleteTwoFactorOTP(TwoFactorOTP twoFactorOTP) {
        twoFactorOtpRep.delete(twoFactorOTP);

    }
}
