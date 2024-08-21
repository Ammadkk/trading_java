package com.market.trading.service;

import com.market.trading.Model.User;
import com.market.trading.domain.VerificationType;

public interface UserService {

    public User findUserProfileByJwt(String jwt) throws Exception;

    public User findUserByEmail(String email) throws Exception;

    public User findUserById(Long userId) throws Exception;

    public User enableTwoFactorAuthentication(VerificationType verificationType
            ,String sendTo
            ,User user);

    User updatePassword(User user,String newPassword);
}
