package com.market.trading.service;


import com.market.trading.Model.PaymentOrder;
import com.market.trading.Model.User;
import com.market.trading.domain.PaymentMethod;
import com.market.trading.response.PaymentResponse;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;

public interface PaymentService {

    public PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod);

    public PaymentOrder getPaymentOrderById(Long id) throws Exception;

    Boolean proceedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException;

    PaymentResponse createRazorPaymentLink(User user, Long amount, Long orderId) throws RazorpayException;

    PaymentResponse createStripePaymentLink(User user, Long amount, Long orderId) throws StripeException;

}
