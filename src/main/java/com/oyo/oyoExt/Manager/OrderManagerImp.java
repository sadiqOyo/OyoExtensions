package com.oyo.oyoExt.Manager;

import com.google.gson.Gson;
import com.oyo.oyoExt.Entities.OrderEntity;
import com.oyo.oyoExt.Request.OrderRequest;
import com.oyo.oyoExt.Request.Products;
import com.oyo.oyoExt.repositry.OrderRepositry;
import com.oyo.paymentgatewayscommon.enums.StatusCode;
import com.oyo.payments.response.WrapperResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

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
        Double totalAmount = orderRequest.getProducts().stream().collect(Collectors.summingDouble(Products::getAmount));
        orderEntity.setTotalAmount(totalAmount);
        orderEntity.setCurrency(orderRequest.getCurrency());
        orderEntity.setCategory(orderRequest.getCategory());
        orderEntity.setCreatedAt(Date.from(Instant.now()));
        orderEntity.setUpdatedAt(Date.from(Instant.now()));
        orderEntity.setUserProfileId(orderRequest.getUserProfileId());
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
    public WrapperResponse<?> modifyOrder(String orderId, Boolean isPaid, String trxnId)  {

        OrderEntity existingOrder = orderRepositry.findByOrderId(orderId);
        if(Objects.isNull(existingOrder))
            return WrapperResponse.<OrderEntity>builder().
                    statusCode(String.valueOf(StatusCode.BAD_REQUEST)).statusMessage("no data found").build();
        existingOrder.setIsPaid(isPaid);
        existingOrder.setTrxn_id(trxnId);
        orderRepositry.save(existingOrder);
        return WrapperResponse.<OrderEntity>builder().data(existingOrder).build();
    }




}
