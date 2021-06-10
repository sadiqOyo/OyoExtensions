package com.oyo.oyoExt.Request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.oyo.paymentgatewayscommon.enums.Aggregator;
import com.oyo.paymentgatewayscommon.enums.Currency;
import com.oyo.paymentgatewayscommon.enums.Gateway;
import com.oyo.paymentgatewayscommon.enums.Language;
import com.oyo.paymentgatewayscommon.enums.OrderType;
import com.oyo.paymentgatewayscommon.enums.PaymentMode;
import com.oyo.paymentgatewayscommon.enums.PaymentType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.json.simple.JSONObject;

import javax.validation.constraints.NotNull;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InitiatePaymentReq {
    @NotNull
    @NotBlank
    @JsonProperty("merchant_txn_id")
    @ApiModelProperty(notes = "merchantTxnId of the transaction", required = true,
            example = "adsfjaklsdf8989")
    private String merchantTxnId;

    private String paymentTxnId;

    @NotNull
    @NotBlank
    @JsonProperty("merchant_id")
    @ApiModelProperty(notes = "id of then merchant who will make this call", required = true,
            example = "2738f2b4-a35d-4c33-a26a-e4d9583778a5")
    private UUID merchantId;

    @NotNull
    @NotBlank
    @JsonProperty("channel_id")
    @ApiModelProperty(notes = "id of then channel from where this call will be invoked",
            required = true, example = "211f2406-d211-4f6b-b8b3-4532248ee4b0")
    private UUID channelId;

    @NotNull
    @NotBlank
    @JsonProperty("user_profile_id")
    @ApiModelProperty(notes = "user profile id", required = true, example = "2582353")
    private String userProfileId;

    @NotNull
    @NotBlank
    @JsonProperty("order_id")
    @ApiModelProperty(notes = "id of the order for which transaction has to be initiated",
            required = true, example = "2582353")
    private String orderId;

    @NotNull
    @NotBlank
    @JsonProperty("order_type")
    @ApiModelProperty(notes = "type of order", required = true, example = "BOOKING")
    private OrderType orderType;

    @NotNull
    @JsonProperty("amount")
    @ApiModelProperty(notes = "amount of transaction", required = true, example = "1000.0")
    private Double amount;

    @NotNull
    @NotBlank
    @JsonProperty("currency")
    @ApiModelProperty(notes = "currency in which payment has to be made", required = true,
            example = "INR")
    private Currency currency;

    @NotNull
    @JsonProperty("order_amount")
    @ApiModelProperty(notes = "amount of the trasaction in the currency in which order has been made",
            required = true, example = "1600.0")
    private Double orderAmount;

    @NotNull
    @NotBlank
    @JsonProperty("order_currency")
    @ApiModelProperty(notes = "currency in which order has been made", required = true,
            example = "NPR")
    private Currency orderCurrency;

    @JsonProperty("gateway")
    @ApiModelProperty(notes = "gateway which is to be used for taking user payment", required = false,
            example = "PAYTM")
    private Gateway gateway;

    @NotNull
    @NotBlank
    @JsonProperty("aggregator")
    @ApiModelProperty(notes = "aggregator which is to be used to interact with gateway ",
            required = true, example = "JUSPAY")
    private Aggregator aggregator;

    @JsonProperty("success_url")
    private String successUrl;

    @JsonProperty("failure_url")
    private String failureUrl;

    @JsonProperty("return_url")
    private String returnUrl;

    @JsonProperty("language")
    private Language language;

    @JsonProperty("description")
    private String description;

    @NotNull
    @NotBlank
    @JsonProperty("payment_mode")
    @ApiModelProperty(notes = "payment mode for this transaction", required = true, example = "CC")
    private PaymentMode paymentMode;

    @JsonProperty("customer_id")
    @ApiModelProperty(notes = "customer id", required = true, example = "2582353")
    private String customerId;

    @JsonProperty("product_info")
    private String productInfo;

    @JsonProperty("coupon_code")
    @ApiModelProperty(notes = "coupon code for this transaction", required = false,
            example = "PAYTM_OFF")
    private String couponCode;

    @JsonProperty("order_details")
    private String orderDetails;

    @JsonProperty("bank_code")
    private String bankCode;

    @JsonProperty("card_type")
    private String cardType;

    @JsonProperty("use_merchant_txn_id")
    @ApiModelProperty(
            notes = "if merchantTxnId is to be used to interact with gateway or new should be created by payment service",
            required = false, example = "true")
    private Boolean useMerchantTxnId;

    @JsonProperty("customer_id_by_client")
    @ApiModelProperty(notes = "encrypted customer id", required = false)
    private String customerIdByClient;

    @JsonProperty("email")
    @ApiModelProperty(notes = "email address of user", required = true, example = "abc@oyorooms.com")
    private String email;

    @JsonProperty("phone")
    @ApiModelProperty(notes = "phone number of user", required = true, example = "9999123456")
    private String phone;

    @JsonProperty("payer_phone")
    private String payerPhone;

    @JsonProperty("payer_otp")
    private String payerOTP;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("city_name")
    private String cityName;

    private String merchantName;

    private String channelName;

    @JsonProperty("wallet_id")
    private String walletId;

    @JsonProperty("country_code")
    @ApiModelProperty(notes = "country code of settlement country", required = true, example = "IN")
    private String countryCode;

    @JsonProperty("shopper_locale")
    private String shopperLocale;

    @JsonProperty("payment_service_params")
    private JSONObject paymentServiceParams = new JSONObject();


    @JsonProperty("collect_cards")
    private boolean collectCards;

    @ApiModelProperty(notes = "Override backend traffic control to external gateway servicce",
            example = "true")
    @JsonProperty("use_external_service")
    private Boolean useExternalService;

    @JsonProperty("payment_type")
    private PaymentType paymentType;

    @ApiModelProperty(notes = "PayLater")
    private boolean paylater;

    @JsonIgnore
    private String traceId;
}
