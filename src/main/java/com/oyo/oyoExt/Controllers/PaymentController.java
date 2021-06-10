package com.oyo.oyoExt.Controllers;


import com.oyo.oyoExt.Manager.PaymentManager;
import com.oyo.oyoExt.Request.PaymentRequest;
import com.oyo.payments.response.WrapperResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/payment"})
@Api("Payment")
public class PaymentController {

    @Autowired
    private PaymentManager paymentManager;

    @PostMapping("/partial")
    public WrapperResponse<?> partialPayment(
            @RequestBody PaymentRequest createOrderRequest,
            @RequestParam (value = "orderId") String orderId) throws Exception {
        return paymentManager.partialPayment(orderId);
    }

    @PostMapping("/full")
    public WrapperResponse<?> fullPayment(
            @RequestBody PaymentRequest createOrderRequest,
            @RequestParam (value = "bookingId") String bookingId) {


        return null;
    }

    @GetMapping("/invoce")
    public WrapperResponse<?> getInvocePayment(
            @RequestParam (value = "bookingId") String bookingId) {
        return null;
    }

}
