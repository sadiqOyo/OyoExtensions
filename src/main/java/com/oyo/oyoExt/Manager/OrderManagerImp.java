package com.oyo.oyoExt.Manager;

import com.google.gson.Gson;
import com.oyo.oyoExt.Entities.OrderEntity;
import com.oyo.oyoExt.Request.OrderRequest;
import com.oyo.oyoExt.Request.Products;
import com.oyo.oyoExt.repositry.OrderRepositry;
import com.oyo.paymentgatewayscommon.enums.StatusCode;
import com.oyo.paymentgatewayscommon.parser.IGson;
import com.oyo.paymentgatewayscommon.utilities.IUtil;
import com.oyo.payments.response.WrapperResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderManagerImp implements OrderManager{

    @Autowired
    private OrderRepositry orderRepositry;

    @Override
    public WrapperResponse<?> addOrder(OrderRequest orderRequest) {
        OrderEntity orderEntity=new OrderEntity();
        Gson gson = new Gson();
        String productString = gson.toJson(orderRequest.getProducts());
        orderEntity.setProducts(productString);
        orderEntity.setOrderId(String.valueOf(UUID.randomUUID()));
        orderEntity.setBookingId(orderRequest.getBookingId());
        orderEntity.setIsPaid(orderRequest.getIsPaid());
        Long totalAmount = orderRequest.getProducts().stream().collect(Collectors.summingLong(Products::getPrice));
        orderEntity.setTotalAmount(totalAmount);
        orderEntity.setCategory(orderRequest.getCategory());
        orderEntity.setCreatedAt(Date.from(Instant.now()));
        orderEntity.setUpdatedAt(Date.from(Instant.now()));
        orderRepositry.save(orderEntity);
        return WrapperResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

    @Override
    public WrapperResponse<?> viewOrderByOrderId(String orderId)  {

        OrderEntity order = orderRepositry.findByOrderId(orderId);
        if(Objects.isNull(order))
            return WrapperResponse.<OrderEntity>builder().
                    statusMessage("No Order Found, please provide correct order id").statusCode(String.valueOf(StatusCode.BAD_REQUEST)).build();

        return WrapperResponse.<OrderEntity>builder().data(order).build();

    }

    @Override
    public WrapperResponse<?> viewOrderByBookingId(String bookingId)  {

        List<OrderEntity> orderEntityList = orderRepositry.findAllByBookingId(bookingId);
        if(orderEntityList.size() == 0)
            return WrapperResponse.<OrderEntity>builder().
                    statusMessage("No Order Found, please provide correct booking id").statusCode(String.valueOf(StatusCode.BAD_REQUEST)).build();

        return WrapperResponse.<List<OrderEntity>>builder().data(orderEntityList).build();

    }

    @Override
    public WrapperResponse<?> modifyOrder(OrderRequest modifyOrderRequest, String orderId)  {

        OrderEntity existingOrder = orderRepositry.findByOrderId(orderId);
        if(Objects.isNull(existingOrder))
            return WrapperResponse.<OrderEntity>builder().
                    statusCode(String.valueOf(StatusCode.BAD_REQUEST)).statusMessage("no data found").build();
        existingOrder.setIsPaid(modifyOrderRequest.getIsPaid());
        orderRepositry.save(existingOrder);
        return WrapperResponse.<OrderEntity>builder().data(existingOrder).build();
    }



    @Override public WrapperResponse<?> payOrder(String orderId, String bookingId) {
//        OrderEntity order = orderRepositry.findByOrderIdAndAndBookingId(orderId,bookingId);
//        if(Objects.isNull(order))
//            throw new Exception("Order not found"+orderId);
//        order.setIsPaid(true);
//        orderRepositry.save(order);
        return WrapperResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }
}
