package com.oyo.oyoExt.Manager;

import com.oyo.oyoExt.Entities.OrderEntity;
import com.oyo.oyoExt.Request.Order;
import com.oyo.oyoExt.Request.Products;

import java.util.List;

public interface OrderManager {

    public void addProduct(List<Products> productsList,String bookingId,Boolean isPaid);

    public Order viewOrder(String orderId,String bookingId) throws Exception;

    public void modifyOrder(Order order) throws Exception;

    public boolean payOrder(String orderId,String bookingId) throws Exception;
}
