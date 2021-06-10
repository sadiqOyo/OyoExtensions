package com.oyo.oyoExt.Response;

import com.oyo.oyoExt.Request.Order;
import com.oyo.oyoExt.Request.Products;

import java.util.List;

public class InvoiceResponse {

    private String orderId;
    private Long totalAmount;
    private List<Order> orders;
    private Long remainingAmount;
}
