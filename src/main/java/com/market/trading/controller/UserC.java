package com.market.trading.controller;


import com.market.trading.request.ForgotPasswordTokenRequest;
import com.market.trading.Model.ForgotPasswordToken;
import com.market.trading.Model.User;
import com.market.trading.Model.VerificationCode;
import com.market.trading.Utils.OtpUtils;
import com.market.trading.domain.VerificationType;
import com.market.trading.request.ResetPasswordRequest;
import com.market.trading.response.ApiResponse;
import com.market.trading.response.AuthResponse;
import com.market.trading.service.EmailService;
import com.market.trading.service.ForgotPasswordService;
import com.market.trading.service.UserService;
import com.market.trading.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserC {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private ForgotPasswordService forgotPasswordService;


    @GetMapping("/api/users/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);


        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("/api/users/{verificationType}/send-otp")

    public ResponseEntity<String> sendVerificationOtp(
            @RequestHeader("Authorization")
            @PathVariable VerificationType verificationType,
            String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode = verificationCodeService
                .getVerificationCodeByUser(user.getId());


        if (verificationCode == null) {
            verificationCode = verificationCodeService.sendVerificationCode(user, verificationType);
        }

        if (verificationType.equals(VerificationType.EMAIL)) {
            emailService.sendVerificationOtpEmail(user.getEmail(), verificationCode.getOtp());
        }

        return new ResponseEntity<>("Verification otp sent successfully", HttpStatus.OK);
    }


    @PatchMapping("/api/users/enable-two-factor/verify-otp/{otp}")

    public ResponseEntity<User> enableTwoFactorAuthentication(
            @PathVariable String otp,
            @RequestHeader("Authorization")
            String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);


        VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());


        String sendTo = verificationCode.getVerificationType().equals(VerificationType.EMAIL) ?
                verificationCode.getEmail() : verificationCode.getMobile();

        boolean isVerified = verificationCode.getOtp().equals(otp);

        if (isVerified) {


            User updatedUser = userService.enableTwoFactorAuthentication(
                    verificationCode.getVerificationType(), sendTo, user);

            verificationCodeService.deleteVerificationCodeById(verificationCode);

            return new ResponseEntity<>(updatedUser, HttpStatus.OK);

        }

        throw new Exception("Wrong otp");
    }

    @PostMapping("/auth/users/reset-password/send-otp")

    public ResponseEntity<AuthResponse> sendForgotPasswordOtp(
            @RequestBody ForgotPasswordTokenRequest req) throws Exception {

        User user = userService.findUserByEmail(req.getSendTo());
        String otp = OtpUtils.generateOTP();

        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();

//findByUser ye wo token hai jo user request krta hai or phir already exist match process..

        ForgotPasswordToken token = forgotPasswordService.findByUser(user.getId());
        if (token == null) {
            token = forgotPasswordService.createToken(user,id,otp,req.getVerificationType(),req.getSendTo());
        }

        if (req.getVerificationType().equals(VerificationType.EMAIL)) {
            emailService.sendVerificationOtpEmail(user.getEmail(), token.getOtp());
        }

        AuthResponse res = new AuthResponse();
        res.setSessionId(token.getId());
        res.setMessage("Password reset otp sent successfully");

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PatchMapping("/auth/users/reset-passwordr/verify-otp")

    public ResponseEntity<ApiResponse> resetPassword(
            @RequestParam String id,
            @RequestBody ResetPasswordRequest req,
            @RequestHeader("Authorization")
            String jwt) throws Exception {

//findById retrieve specific token hota hai har OTP ka, token is searched based on token's unique ID
        ForgotPasswordToken forgotPasswordToken = forgotPasswordService.findById(id);

        boolean isVerified = forgotPasswordToken.getOtp().equals(req.getOtp());

        if (isVerified) {
            userService.updatePassword(forgotPasswordToken.getUser(),req.getPassword());
            ApiResponse res = new ApiResponse();
            res.setMessage("Password updated successfully!");
            return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
        }
        throw new Exception("Wrong otp");

    }




}


