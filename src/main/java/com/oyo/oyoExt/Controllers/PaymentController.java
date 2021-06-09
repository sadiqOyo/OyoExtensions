package com.oyo.oyoExt.Controllers;

import com.oyo.oyoExt.Request.CreateOrderRequest;
import com.oyo.payments.response.WrapperResponse;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/payment"})
@Api("Payment")
public class PaymentController {

    @PostMapping("/partial")
    public WrapperResponse<?> partialPayment(
            @RequestBody CreateOrderRequest createOrderRequest) {

        return null;
    }
}
