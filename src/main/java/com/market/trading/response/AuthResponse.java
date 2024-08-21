package com.market.trading.response;


import lombok.Data;

@Data
public class AuthResponse {

    private String jwt;
    private String message;
    private String sessionId;
    private boolean status;
    private boolean isTwoFactorAuthEnabled;
}
