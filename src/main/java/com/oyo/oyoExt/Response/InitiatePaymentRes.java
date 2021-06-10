package com.oyo.oyoExt.Response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.oyo.paymentgatewayscommon.enums.Aggregator;
import com.oyo.paymentgatewayscommon.enums.Gateway;
import com.oyo.paymentgatewayscommon.enums.PaymentMode;
import com.oyo.paymentgatewayscommon.enums.TransactionStatus;
import com.oyo.paymentgatewayscommon.response.PaymentLinks;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.json.simple.JSONObject;

import java.util.Date;

public class InitiatePaymentRes {
    @JsonProperty("payment_txn_id")
    @ApiModelProperty(notes = "transaction id created by payments service to interact with gateway",
            required = true, example = "sldfjakld999riwer")
    private String payment_txn_id;

    @JsonProperty("aggregator_txn_id")
    @ApiModelProperty(
            notes = "transaction id provided by aggregator if aggregator is used to interact with gateway",
            required = true, example = "aggklsdjlfka989843k")
    private String aggregator_txn_id;

    @JsonProperty("gateway_txn_id")
    @ApiModelProperty(
            notes = "transaction id provided by gateway if a call has been made to gateway in this call",
            required = true, example = "gateway999riwer")
    private String gateway_txn_id;

    @JsonProperty("bank_txn_id")
    @ApiModelProperty(
            notes = "transaction id provided by bank if a call has been made to bank directly or via gateway and gateway provides that detail in this call",
            required = true, example = "bank999riwer")
    private String bank_txn_id;

    @JsonProperty("payments_links")
    @ApiModelProperty(
            notes = "payment links to be used for payments in case of juspay aggregator and for other gateways if other gateway provides",
            required = true)
    private PaymentLinks payments_links;

    @JsonProperty("pg_status")
    @ApiModelProperty(notes = "status of transaction provided by gateway/aggregator",
            required = false)
    private String pg_status;

    @JsonProperty("status")
    @ApiModelProperty(notes = "status of transaction at payments service", required = true)
    private TransactionStatus status;

    @JsonProperty("pg_response")
    @ApiModelProperty(notes = "response of gateway for this call in json format", required = true)
    private JSONObject pg_response;

    @JsonProperty("response")
    @ApiModelProperty(notes = "response created by payment service which will be used by client",
            required = true)
    private JSONObject response;

    @JsonProperty("txn_metadata")
    private JSONObject txn_metadata;

    @JsonIgnore
    private Date createdAt;

    @JsonProperty("response_msg")
    private String response_msg;

    @JsonProperty("offline_payer")
    private JSONObject offline_payer;

    @JsonProperty("payment_mode")
    @ApiModelProperty(notes = "payment mode of this transaction", required = true, example = "CC")
    private PaymentMode payment_mode;

    @JsonProperty("gateway")
    private Gateway gateway;

    @JsonProperty("crs_gateway")
    private String crs_gateway;

    private Aggregator aggregator;

    @JsonProperty("public_key")
    private PublicKey public_key;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("web_browser")
    private String web_browser;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
