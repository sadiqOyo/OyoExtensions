package com.oyo.oyoExt.repositry;

import com.oyo.oyoExt.Entities.OrderEntity;
import com.oyo.oyoExt.Request.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepositry extends JpaRepository<OrderEntity, Long> {

    OrderEntity findByOrderIdAndAndBookingId(String orderId, String bookingId);

    OrderEntity findByOrderId(String orderId);

    List<Order> findByBookingId(String bookingId);
}
