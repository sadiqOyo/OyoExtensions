package com.oyo.oyoExt.Manager;

import com.oyo.oyoExt.Request.Order;
import com.oyo.oyoExt.Request.OrderRequest;
import com.oyo.oyoExt.Request.Products;
import com.oyo.payments.response.WrapperResponse;

import java.util.List;

public interface OrderManager {

    public WrapperResponse<?> addOrder(OrderRequest orderRequest);

    public WrapperResponse<?> viewOrderByOrderId(String orderId) ;

    public WrapperResponse<?> viewOrderByBookingId(String bookingId) ;

    public WrapperResponse<?> modifyOrder(OrderRequest order, String orderId) ;

    public WrapperResponse<?> payOrder(String orderId,String bookingId);

}
