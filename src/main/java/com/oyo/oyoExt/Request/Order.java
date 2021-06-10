package com.oyo.oyoExt.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    private String orderId;
    private String Category;
    private List<Products> products;
    private Long totalAmount;
    private Boolean isPaid;
    private String bookingId;

}
