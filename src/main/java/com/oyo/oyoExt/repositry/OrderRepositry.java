package com.oyo.oyoExt.repositry;

import com.oyo.oyoExt.Entities.OrderEntity;
import com.oyo.oyoExt.Request.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepositry extends JpaRepository<OrderEntity, Long> {



    OrderEntity findByOrderId(String orderId);

    List<OrderEntity> findAllByBookingId(String bookingId);

    OrderEntity findByOrderIdAndAndBookingId(String orderId, String bookingId);

    @Query(value = "SELECT * FROM orders o WHERE o.order_id IN (:ids)", nativeQuery = true)
    List<OrderEntity> findByOrderIds(@Param("ids")List<String> orderIds);
}
