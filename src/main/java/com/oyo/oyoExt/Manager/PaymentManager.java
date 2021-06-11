package com.oyo.oyoExt.Manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.oyo.oyoExt.Entities.OrderEntity;
import com.oyo.oyoExt.Request.InitiatePaymentReq;
import com.oyo.oyoExt.Request.OrderIdsRequest;
import com.oyo.oyoExt.Request.VerifyPaymentReq;
import com.oyo.oyoExt.Response.InitiatePaymentRes;
import com.oyo.oyoExt.Response.InvoiceResponse;
import com.oyo.oyoExt.Response.PaymentDataResponse;
import com.oyo.oyoExt.Response.VerifyDataResponse;
import com.oyo.oyoExt.Response.VerifyPaymentResponse;
import com.oyo.oyoExt.repositry.OrderRepositry;
import com.oyo.paymentgatewayscommon.enums.Aggregator;
import com.oyo.paymentgatewayscommon.enums.Currency;
import com.oyo.paymentgatewayscommon.enums.Gateway;
import com.oyo.paymentgatewayscommon.enums.OrderType;
import com.oyo.paymentgatewayscommon.enums.PaymentMode;
import com.oyo.paymentgatewayscommon.enums.StatusCode;
import com.oyo.paymentgatewayscommon.enums.TransactionStatus;
import com.oyo.paymentgatewayscommon.request.VerifyPaymentRequest;
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
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Service
public class PaymentManager {


    @Autowired
    private OrderRepositry orderRepositry;


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderManagerImp orderManagerImp;

    @Autowired
    private Gson gson;

    @Value("${PAYMENTS_BASE_URL}")
    private String baseUrl;


    @Autowired
    @Value("${PAYMENTS_INITIATE_TRANSACTION}")
    private String path;



    public WrapperResponse<?> getInvoice(String bookingId)
    {
        List<OrderEntity> orderEntityList = orderRepositry.findAllByBookingId(bookingId);
        if(orderEntityList.size() == 0){
            return WrapperResponse.<InitiatePaymentResponse>builder().statusMessage("No order found with given order id").
                    statusCode(String.valueOf(HttpStatus.BAD_REQUEST)).build();
        }
        InvoiceResponse invoiceResponse = new InvoiceResponse();
        Double totalAmount = 0.0;
        Double amountRemaining = 0.0;
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
        return WrapperResponse.<InvoiceResponse>builder().data(invoiceResponse).build();
    }

    public WrapperResponse<?> partialPayment(OrderIdsRequest orderIds) throws Exception {
        List<OrderEntity> orderEntityList = orderRepositry.findByOrderIds(orderIds.getOrders());
        if(orderEntityList.size() == 0){
            return WrapperResponse.<InitiatePaymentResponse>builder().statusMessage("No order found with given order id").
                    statusCode(String.valueOf(HttpStatus.BAD_REQUEST)).build();
        }
        return initiatePayment(orderEntityList, orderEntityList.get(0).getBookingId());


    }

    private WrapperResponse<?> initiatePayment(List<OrderEntity> orderEntityList, String bookingId) {
        InitiatePaymentResponse initiatePaymentResponse = null;
        RestTemplate restTemplate = new RestTemplate();
        InitiatePaymentReq initiatePaymentRequest = getInitiatePaymentRequest(orderEntityList);
        if(Objects.isNull(initiatePaymentRequest)){
            WrapperResponse<?> invoiceResponse = getInvoice(bookingId);

            InvoiceResponse invres = (InvoiceResponse) invoiceResponse.getData();
            //gson.fromJson(invoiceResponse.getData().toString(), InvoiceResponse.class);
            invres.getOrders().stream().filter(o -> orderEntityList.contains(o.getOrderId()));
           return WrapperResponse.<InvoiceResponse>builder().statusMessage("Orders Already Charged").
                    statusCode(String.valueOf(HttpStatus.ALREADY_REPORTED)).data(invres).build();
        }
        String requestString = gson.toJson(initiatePaymentRequest);
        JSONObject body = objectMapper.convertValue(requestString, JSONObject.class);
        HttpEntity<InitiatePaymentReq> requestEntity = new HttpEntity<>(initiatePaymentRequest, new HttpHeaders());
        MappingJackson2HttpMessageConverter jsonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
        jsonHttpMessageConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        restTemplate.getMessageConverters().add(jsonHttpMessageConverter);
        ResponseEntity<String> paymentResponse = restTemplate.exchange(baseUrl+path, HttpMethod.POST,requestEntity,String.class);
        PaymentDataResponse paymentDataResponse = new PaymentDataResponse();
        if(paymentResponse.getStatusCode().equals(HttpStatus.OK)){

//            orderEntityList.stream().forEach(orders -> orderManagerImp.modifyOrder(orders.getOrderId(), Boolean.TRUE, initiatePaymentRequest.getMerchantTxnId()));
//            try {
                paymentDataResponse = gson.fromJson(paymentResponse.getBody(), PaymentDataResponse.class);
//                        objectMapper.readValue(paymentResponse.getBody(), PaymentDataResponse.class);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            return WrapperResponse.<InitiatePaymentRes>builder().data(paymentDataResponse.getData()).build();
        }

        return WrapperResponse.<InitiatePaymentResponse>builder().statusMessage("Payment Failed").
                statusCode(String.valueOf(HttpStatus.BAD_REQUEST)).build();
    }

    private InitiatePaymentReq getInitiatePaymentRequest(List<OrderEntity> orderEntity) {
        OrderEntity order = orderEntity.get(0);
        Currency currency = order.getCurrency();
        String merchantTxnId = orderEntity.size()== 1?order.getOrderId():order.getBookingId();
        String userProfileId = order.getUserProfileId();
        Double totalAmount = orderEntity.stream().filter(o -> o.getIsPaid().equals(Boolean.FALSE)).collect(Collectors.summingDouble(OrderEntity::getTotalAmount));
        if(totalAmount == 0){
            return null;
        }
        InitiatePaymentReq initiatePaymentReq = InitiatePaymentReq.builder().amount(totalAmount).
                aggregator(Aggregator.DEFAULT).gateway(Gateway.PAYU).
                channelId(UUID.fromString("211f2406-d211-4f6b-b8b3-4532248ee4b0")).
                countryCode("IN").collectCards(Boolean.FALSE).currency(currency).orderCurrency(currency).
                orderId(merchantTxnId).merchantId(UUID.fromString("2738f2b4-a35d-4c33-a26a-e4d9583778a5")).
                orderAmount(totalAmount).orderType(OrderType.BOOKING).amount(totalAmount).paymentMode(PaymentMode.CC).
                merchantTxnId(String.valueOf(UUID.randomUUID())).userProfileId(userProfileId)
                .build();
        return initiatePaymentReq;
    }

    public WrapperResponse<?> fullPayment(String bookingId)  {

        List<OrderEntity> orderEntityList = orderRepositry.findAllByBookingId(bookingId);
        if(orderEntityList.size() == 0){
            return WrapperResponse.<InitiatePaymentResponse>builder().statusMessage("No order found with given booking id").
                    statusCode(String.valueOf(HttpStatus.BAD_REQUEST)).build();
        }
        return initiatePayment(orderEntityList, bookingId);
    }

    public WrapperResponse<?> verifyPayment(String bookingId) {
        List<OrderEntity> orderEntityList = orderRepositry.findAllByBookingId(bookingId);
        if(CollectionUtils.isEmpty(orderEntityList)){
            return WrapperResponse.<InitiatePaymentResponse>builder().statusMessage("No order found with given booking id").
                    statusCode(String.valueOf(HttpStatus.BAD_REQUEST)).build();
        }
        orderEntityList = orderEntityList.stream().filter(distinctByKey(OrderEntity::getTrxn_id)).collect(Collectors.toList());
        RestTemplate restTemplate = new RestTemplate();
        for(OrderEntity order : orderEntityList){
            VerifyPaymentReq verifyPaymentReq = VerifyPaymentReq.builder().merchantTxnId(order.getTrxn_id()).build();
            HttpEntity<VerifyPaymentReq> verifyEntity = new HttpEntity<>(verifyPaymentReq);
            ResponseEntity<String> response = restTemplate.exchange(baseUrl+"/transaction/verify",
                    HttpMethod.PUT,verifyEntity,String.class);
            VerifyDataResponse verifyDataResponse = new VerifyDataResponse();
            if(response.getStatusCode().equals(HttpStatus.OK)){
                verifyDataResponse = gson.fromJson(response.getBody(), VerifyDataResponse.class);
                if(verifyDataResponse.getData().getStatus().equals(TransactionStatus.PENDING)){
                    orderManagerImp.modifyOrder(order.getOrderId(), Boolean.FALSE, verifyPaymentReq.getMerchantTxnId());
                }else if(verifyDataResponse.getData().getStatus().equals(TransactionStatus.SUCCESS)){
                    orderManagerImp.modifyOrder(order.getOrderId(), Boolean.TRUE, verifyPaymentReq.getMerchantTxnId());
                }
            }
        }
        WrapperResponse<?> invoiceResponse = getInvoice(bookingId);
        InvoiceResponse invcRes = (InvoiceResponse) invoiceResponse.getData();
        return WrapperResponse.<InvoiceResponse>builder().data(invcRes).build();
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
