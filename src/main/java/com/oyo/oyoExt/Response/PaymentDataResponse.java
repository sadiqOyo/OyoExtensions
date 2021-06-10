package com.oyo.oyoExt.Response;

import com.oyo.paymentgatewayscommon.response.InitiatePaymentResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDataResponse {
    private InitiatePaymentRes data;
}
