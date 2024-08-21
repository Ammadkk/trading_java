package com.market.trading.Model;


import com.market.trading.domain.VerificationType;
import lombok.Data;

@Data

public class TwoFactorAuth {

    private boolean isEnabled=false;
    private VerificationType sendTo;


}
