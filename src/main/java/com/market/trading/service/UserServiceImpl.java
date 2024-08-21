package com.market.trading.service;

import com.market.trading.Model.TwoFactorAuth;
import com.market.trading.Model.User;
import com.market.trading.configuration.JwtProvider;
import com.market.trading.domain.VerificationType;
import com.market.trading.repository.UserRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRep userRep;



    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {
        String email = JwtProvider.getEmailFromToken(jwt);
        User user = userRep.findByEmail(email);
        if (user==null){
            throw new Exception("User not found");
        }
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRep.findByEmail(email);
        if (user==null){
            throw new Exception("User not found");
        }
        return user;
    }

    @Override
    public User findUserById(Long userId) throws Exception {
        Optional<User> user = userRep.findById(userId);
        if (user.isEmpty()){
            throw new Exception("user not found");
        }

        return user.get();
    }

    @Override
    public User enableTwoFactorAuthentication(VerificationType verificationType, String sendTo, User user) {

        TwoFactorAuth twoFactorAuth = new TwoFactorAuth();
        twoFactorAuth.setEnabled(true);
        twoFactorAuth.setSendTo(verificationType);
        user.setTwofactorAuth(twoFactorAuth);

        return userRep.save(user);
    }


    @Override
    public User updatePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        return userRep.save(user);
    }
}
