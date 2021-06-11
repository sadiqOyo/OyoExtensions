package com.oyo.oyoExt.Response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.oyo.paymentgatewayscommon.enums.Gateway;
import com.oyo.paymentgatewayscommon.enums.PaymentMode;
import com.oyo.paymentgatewayscommon.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.simple.JSONObject;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerifyPaymentResponse {
    @JsonProperty("gateway_txn_id")
    private String gatewayTxnId;

    @JsonIgnore
    private String merchantTxnId;

    @JsonProperty("payment_txn_id")
    private String paymentTxnId;

    @JsonProperty("bank_txn_id")
    private String bankTxnId;

    @JsonProperty("aggregator_txn_id")
    private String aggregatorTxnId;

    @JsonProperty("pg_status")
    private String pgStatus;

    @JsonProperty("status")
    private TransactionStatus status;

    @JsonProperty("pg_response")
    private JSONObject pgResponse;

    @JsonProperty("bank_error_code")
    private String bankErrorCode;

    @JsonProperty("bank_error_message")
    private String bankErrorMessage;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("response_msg")
    private String responseMsg;

    private Gateway gateway;

    @JsonProperty("metadata")
    private JSONObject txnMetadata;

    @JsonProperty("payment_mode")
    private PaymentMode paymentMode;

    @JsonProperty("response")
    private JSONObject response;

    @JsonProperty("crs_gateway")
    private String crsGateway;

    @JsonProperty("display_message")
    private String displayMessage;

}
