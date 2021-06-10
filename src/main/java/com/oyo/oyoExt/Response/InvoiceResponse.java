package com.oyo.oyoExt.Response;

import com.oyo.oyoExt.Entities.OrderEntity;
import com.oyo.oyoExt.Request.Order;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceResponse {

    private String bookingId;
    private Double totalAmount;
    private List<OrderEntity> orders;
    private Double remainingAmount;
}
