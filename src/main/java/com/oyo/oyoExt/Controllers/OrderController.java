package com.oyo.oyoExt.Controllers;

import com.oyo.oyoExt.Entities.OrderEntity;
import com.oyo.oyoExt.Manager.OrderManagerImp;
import com.oyo.oyoExt.Request.ModifyOrderRequest;
import com.oyo.oyoExt.Request.OrderRequest;
import com.oyo.paymentgatewayscommon.enums.StatusCode;
import com.oyo.payments.response.WrapperResponse;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    OrderManagerImp orderManagerImp;

    @PostMapping("/create")
    public WrapperResponse<?> createOrder(
            @RequestBody OrderRequest orderRequest) {

        return orderManagerImp.addOrder(orderRequest);
    }

    @GetMapping("/view")
    public WrapperResponse<?> viewOrder(
            @RequestParam(value = "booking_id", required = false) String bookingId,
            @RequestParam(value = "order_id", required = false) String orderId) throws Exception {
        if(!StringUtils.isEmpty(bookingId)){
            return orderManagerImp.viewOrderByBookingId(bookingId);
        }else if(!StringUtils.isEmpty(orderId)){
            return orderManagerImp.viewOrderByOrderId(orderId);
        }
        return WrapperResponse.<OrderEntity>builder().
                statusMessage("Please provide order id or booking id").statusCode(String.valueOf(StatusCode.BAD_REQUEST)).build();
    }

    @PatchMapping("/modify")
    public WrapperResponse<?> modifyOrder(@RequestParam(value = "order_id", required = true) String orderId,
            @RequestParam(value = "is_paid", required = true) Boolean isPaid) {

        return orderManagerImp.modifyOrder(orderId, isPaid);
    }

}
