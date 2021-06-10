package com.oyo.oyoExt.Controllers;


import com.oyo.oyoExt.Manager.PaymentManager;
import com.oyo.oyoExt.Request.OrderIdsRequest;
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

import java.util.List;

@RestController
@RequestMapping(value = {"/payment"})
@Api("Payment")
public class PaymentController {

    @Autowired
    private PaymentManager paymentManager;

    @PostMapping("/partial")
    public WrapperResponse<?> partialPayment(
            @RequestBody OrderIdsRequest orderIdsList) throws Exception {
        return paymentManager.partialPayment(orderIdsList);
    }

    @PostMapping("/full")
    public WrapperResponse<?> fullPayment(
            @RequestParam (value = "booking_id") String bookingId) {
        return paymentManager.fullPayment(bookingId);

    }

    @GetMapping("/invoice")
    public WrapperResponse<?> getInvocePayment(
            @RequestParam (value = "booking_id") String bookingId) {
        return paymentManager.getInvoice(bookingId);
    }

}
