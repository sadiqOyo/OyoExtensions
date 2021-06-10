package com.oyo.oyoExt.Manager;

import com.oyo.oyoExt.Request.Order;
import com.oyo.oyoExt.Request.Products;
import com.oyo.oyoExt.repositry.OrderRepositry;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

public class OrderManagerImp implements OrderManager {

    @Autowired
    private OrderRepositry orderRepositry;

    @Override
    public void addProduct(List<Products> productsList, String bookingId, Boolean isPaid) {
        Order order=new Order();
        order.setProducts(productsList);
        order.setBookingId(bookingId);
        order.setIsPaid(isPaid);
        orderRepositry.save(order);
    }

    @Override public Order viewOrder(String orderId, String bookingId) throws Exception {
        Order order = orderRepositry.findByOrderIdAndAndBookingId(orderId,bookingId);
        if(Objects.isNull(order))
            throw new Exception("Order not found"+orderId);

        return order;
    }

    @Override public void modifyOrder(Order order) throws Exception {
        Order existingOrder = orderRepositry.findByOrderIdAndAndBookingId(order.getOrderId(),
            order.getBookingId());
        if(Objects.isNull(existingOrder))
            throw new Exception("Order not found"+order.getOrderId());
        orderRepositry.save(order);
    }

    @Override public boolean payOrder(String orderId, String bookingId) throws Exception {
        Order order = orderRepositry.findByOrderIdAndAndBookingId(orderId,bookingId);
        if(Objects.isNull(order))
            throw new Exception("Order not found"+orderId);
        order.setIsPaid(true);
        orderRepositry.save(order);
        return true;
    }
}
