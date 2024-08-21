package com.market.trading.service;

import com.market.trading.Model.PaymentDetails;
import com.market.trading.Model.User;

public interface PaymentDetailService {

    public PaymentDetails addPaymentDetails(String accountNumber,
                                            String accountHolderName,
                                            String ifsc,
                                            String bankName,
                                            User user);

    public PaymentDetails getUsersPaymentDetails(User user);
}
