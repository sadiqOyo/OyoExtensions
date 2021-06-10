package com.oyo.oyoExt.Manager;

import com.oyo.oyoExt.Request.Order;
import com.oyo.oyoExt.Response.InvoiceResponse;
import com.oyo.oyoExt.repositry.OrderRepositry;
import com.oyo.paymentgatewayscommon.response.InitiatePaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class PaymentManager {

<<<<<<< HEAD


=======
    @Autowired
    private OrderRepositry orderRepositry;

    public InvoiceResponse getInvoice(String bookingId)
    {
        InvoiceResponse invoiceResponse = new InvoiceResponse();
        Long totalAmount = null;
        Long amountRemaining = null;
        invoiceResponse.setBookingId(bookingId);
        invoiceResponse.setOrders(orderRepositry.findByBookingId(bookingId));
        for (Order order:invoiceResponse.getOrders()) {
            if(order.getIsPaid())
            {
                amountRemaining+=order.getTotalAmount();
            }
            totalAmount+=order.getTotalAmount();
        }
        invoiceResponse.setRemainingAmount(amountRemaining);
        invoiceResponse.setTotalAmount(totalAmount);
        return invoiceResponse;
    }

    public InitiatePaymentResponse partialPayment(String orderId) throws Exception {
        orderRepositry.findByOrderId(orderId);
        InitiatePaymentResponse response = null;
        //call payment service with orderId and order.amount
        OrderManager orderManager=new OrderManagerImp();
        orderManager.payOrder(orderId);
        return response;
    }

    public InitiatePaymentResponse fullPayment(String bookingId)
    {
       List<Order> orders= orderRepositry.findByBookingId(bookingId);
       InitiatePaymentResponse response = null;
       Long amount = null;
        for (Order order:orders) {
            if(order.getIsPaid())
            {
                amount+=order.getTotalAmount();
            }
        }
//      call payment-service on amount  and booking id
        return response;
    }
>>>>>>> b2ad9f380d5d6b7e54b02a57d01c7b9c29c3f938
}
