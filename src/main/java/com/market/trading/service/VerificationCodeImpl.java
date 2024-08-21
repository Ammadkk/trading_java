package com.market.trading.service;

import com.market.trading.Model.User;
import com.market.trading.Model.VerificationCode;
import com.market.trading.Utils.OtpUtils;
import com.market.trading.domain.VerificationType;
import com.market.trading.repository.VerificationCodeRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VerificationCodeImpl implements VerificationCodeService{


    @Autowired
    private VerificationCodeRep verificationCodeRep;

    @Override
    public VerificationCode sendVerificationCode(User user, VerificationType verificationType) {
        VerificationCode verificationCode1 = new VerificationCode();
        verificationCode1.setOtp(OtpUtils.generateOTP());
        verificationCode1.setVerificationType(verificationType);
        verificationCode1.setUser(user);


        return verificationCodeRep.save(verificationCode1);
    }

    @Override
    public VerificationCode getVerificationCodeById(Long id) throws Exception {
        Optional<VerificationCode> verificationCode = verificationCodeRep.findById(id);
        if (verificationCode.isPresent()){
            return verificationCode.get();
        }
        throw new Exception("verification code not found!");

    }

    @Override
    public VerificationCode getVerificationCodeByUser(Long userId) {
        return verificationCodeRep.findByUserId(userId);
    }

    @Override
    public void deleteVerificationCodeById(VerificationCode verificationCode) {
        verificationCodeRep.delete(verificationCode);

    }
}
