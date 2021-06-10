package com.oyo.oyoExt.Manager;

import com.oyo.oyoExt.Entities.OrderEntity;
import com.oyo.oyoExt.Request.Order;
import com.oyo.oyoExt.Request.Products;
import com.oyo.oyoExt.repositry.OrderRepositry;
import com.oyo.paymentgatewayscommon.utilities.IUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

public class OrderManagerImp implements OrderManager {

    @Autowired
    private OrderRepositry orderRepositry;

    @Autowired
    IUtil iUtil;

    @Override
    public void addProduct(List<Products> productsList, String bookingId, Boolean isPaid) {
        OrderEntity orderEntity=new OrderEntity();
        orderEntity.setProducts(iUtil.createJsonObject(productsList));
        orderEntity.setBookingId(bookingId);
        orderEntity.setIsPaid(isPaid);
        orderRepositry.save(orderEntity);
    }

    @Override public OrderEntity viewOrder(String orderId) throws Exception {
        OrderEntity order = orderRepositry.findByOrderId(orderId);
        if(Objects.isNull(order))
            throw new Exception("Order not found"+orderId);

        return order;
    }

    @Override public List<Order> viewbooking(String bookingId) throws Exception {
        List<Order> orders = orderRepositry.findByBookingId(bookingId);
        if(orders.isEmpty())
            throw new Exception("Booking not found"+bookingId);
        return orders;
    }


    @Override public void modifyOrder(Order order) throws Exception {
        OrderEntity existingOrder = orderRepositry.findByOrderIdAndAndBookingId(order.getOrderId(),
            order.getBookingId());
        if(Objects.isNull(existingOrder))
            throw new Exception("Order not found"+order.getOrderId());
        orderRepositry.save(existingOrder);
    }

    @Override public boolean payOrder(String orderId) throws Exception {
        OrderEntity order = orderRepositry.findByOrderId(orderId);
        if(Objects.isNull(order))
            throw new Exception("Order not found"+orderId);
        order.setIsPaid(true);
        orderRepositry.save(order);
        return true;
    }
}
