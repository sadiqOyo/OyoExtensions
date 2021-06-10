package com.oyo.oyoExt.Manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.oyo.oyoExt.Entities.OrderEntity;
import com.oyo.oyoExt.Request.InitiatePaymentReq;
import com.oyo.oyoExt.Request.Order;
import com.oyo.oyoExt.Request.Products;
import com.oyo.oyoExt.Response.InvoiceResponse;
import com.oyo.oyoExt.repositry.OrderRepositry;
import com.oyo.paymentgatewayscommon.enums.Aggregator;
import com.oyo.paymentgatewayscommon.enums.Currency;
import com.oyo.paymentgatewayscommon.enums.PaymentMode;
import com.oyo.paymentgatewayscommon.enums.StatusCode;
import com.oyo.paymentgatewayscommon.request.AmountRequest;
import com.oyo.paymentgatewayscommon.request.InitiatePaymentRequest;
import com.oyo.paymentgatewayscommon.response.InitiatePaymentResponse;
import com.oyo.payments.response.WrapperResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;


public class PaymentManager {


    @Autowired
    private OrderRepositry orderRepositry;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderManagerImp orderManagerImp;

    @Autowired
    private Gson gson;

    @Autowired
    @Value("${PAYMENTS_BASE_URL}")
    private String baseUrl;


    @Autowired
    @Value("${PAYMENTS_INITIATE_TRANSACTIONL}")
    private String path;


    public InvoiceResponse getInvoice(String bookingId)
    {
        List<OrderEntity> orderEntityList = orderRepositry.findAllByBookingId(bookingId);
        if(orderEntityList.size() == 0){

        }
        InvoiceResponse invoiceResponse = new InvoiceResponse();
        Double totalAmount = null;
        Double amountRemaining = null;
        invoiceResponse.setBookingId(bookingId);
        invoiceResponse.setOrders(orderEntityList);
        for (OrderEntity order:invoiceResponse.getOrders()) {
            if(!order.getIsPaid())
            {
                amountRemaining += order.getTotalAmount();
            }
            totalAmount += order.getTotalAmount();
        }
        invoiceResponse.setRemainingAmount(amountRemaining);
        invoiceResponse.setTotalAmount(totalAmount);
        return invoiceResponse;
    }

    public WrapperResponse<?> partialPayment(String orderId) throws Exception {
        OrderEntity orderEntity = orderRepositry.findByOrderId(orderId);
        if(Objects.isNull(orderEntity)){
            return WrapperResponse.<InitiatePaymentResponse>builder().statusMessage("No order found with given order id").
                    statusCode(String.valueOf(HttpStatus.BAD_REQUEST)).build();
        }
        List<OrderEntity> orderEntityList = new ArrayList<>();
        orderEntityList.add(orderEntity);
        return initiatePayment(orderEntityList);


    }

    private WrapperResponse<?> initiatePayment(List<OrderEntity> orderEntity) {
        InitiatePaymentResponse initiatePaymentResponse = null;
        InitiatePaymentReq initiatePaymentRequest = getInitiatePaymentRequest(orderEntity);
        String requestString = gson.toJson(initiatePaymentRequest);
        JSONObject body = gson.fromJson(requestString, JSONObject.class);
        HttpEntity<JSONObject> requestEntity = new HttpEntity<>(body, new HttpHeaders());
        ResponseEntity<String> paymentResponse = restTemplate.exchange(baseUrl+path, HttpMethod.POST,requestEntity,String.class);
        if(paymentResponse.getStatusCode().equals(HttpStatus.OK)){
            orderEntity.stream().forEach(orders -> orderManagerImp.modifyOrder(orders.getOrderId(), Boolean.TRUE));
            try {
                initiatePaymentResponse = objectMapper.readValue(paymentResponse.getBody(), InitiatePaymentResponse.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return WrapperResponse.<InitiatePaymentResponse>builder().data(initiatePaymentResponse).build();
        }

        return WrapperResponse.<InitiatePaymentResponse>builder().statusMessage("Payment Failed").
                statusCode(String.valueOf(HttpStatus.BAD_REQUEST)).build();
    }

    private InitiatePaymentReq getInitiatePaymentRequest(List<OrderEntity> orderEntity) {
        OrderEntity order = orderEntity.get(0);
        Currency currency = order.getCurrency();
        String merchantTxnId = orderEntity.size()== 1?order.getOrderId():order.getBookingId();
        String userProfileId = order.getUserProfileId();
        Double totalAmount = orderEntity.stream().collect(Collectors.summingDouble(OrderEntity::getTotalAmount));

        InitiatePaymentReq initiatePaymentReq = InitiatePaymentReq.builder().amount(totalAmount).
                aggregator(Aggregator.DEFAULT).
                channelId(UUID.fromString("211f2406-d211-4f6b-b8b3-4532248ee4b0")).
                countryCode("IN").collectCards(Boolean.TRUE).currency(currency).
                orderId(merchantTxnId).merchantId(UUID.fromString("2738f2b4-a35d-4c33-a26a-e4d9583778a5")).
                orderAmount(totalAmount).paymentMode(PaymentMode.CC).
                merchantTxnId(merchantTxnId).userProfileId(userProfileId)
                .build();
        return initiatePaymentReq;
    }

    public WrapperResponse<?> fullPayment(String bookingId) throws IOException {

        List<OrderEntity> orderEntityList = orderRepositry.findAllByBookingId(bookingId);
        if(orderEntityList.size() == 0){
            return WrapperResponse.<InitiatePaymentResponse>builder().statusMessage("No order found with given booking id").
                    statusCode(String.valueOf(HttpStatus.BAD_REQUEST)).build();
        }
        return initiatePayment(orderEntityList);
    }

}
