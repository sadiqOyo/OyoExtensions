package com.oyo.oyoExt.repositry;

import com.oyo.oyoExt.Entities.OrderEntity;
import com.oyo.oyoExt.Request.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepositry extends JpaRepository<OrderEntity, Long> {

<<<<<<< HEAD

    OrderEntity findByOrderId(String orderId);

    List<OrderEntity> findAllByBookingId(String bookingId);
=======
    OrderEntity findByOrderIdAndAndBookingId(String orderId, String bookingId);

    OrderEntity findByOrderId(String orderId);

    List<Order> findByBookingId(String bookingId);
>>>>>>> b2ad9f380d5d6b7e54b02a57d01c7b9c29c3f938
}
