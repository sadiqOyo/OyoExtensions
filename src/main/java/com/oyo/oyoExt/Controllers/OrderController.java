package com.oyo.oyoExt.Controllers;

import com.oyo.oyoExt.Request.CreateOrderRequest;
import com.oyo.payments.response.WrapperResponse;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/order"})
@Api("Order")
public class OrderController {

    @PostMapping("/create")
    public WrapperResponse<?> createOrder(
            @RequestBody CreateOrderRequest createOrderRequest) {

        return null;
    }

    @GetMapping("/view")
    public WrapperResponse<?> viewOrder(
            @RequestParam(value = "booking_id") String bookingId,
            @RequestParam(value = "order_id") String orderId) {

        return null;
    }

    @PatchMapping("/modify")
    public WrapperResponse<?> modifyOrder(
            @RequestParam(value = "booking_id", required = true) String bookingId,
            @RequestParam(value = "order_id", required = true) String orderId) {

        return null;
    }

}
