package com.oyo.oyoExt.repositry;

import com.oyo.oyoExt.Entities.OrderEntity;
import com.oyo.oyoExt.Request.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepositry extends JpaRepository<Order, Long> {

    Order findByOrderIdAndAndBookingId(String orderId, String bookingId);
}
