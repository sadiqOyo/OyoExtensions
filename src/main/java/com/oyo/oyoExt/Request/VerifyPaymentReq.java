package com.oyo.oyoExt.Request;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerifyPaymentReq {

    @NotNull
    @NotBlank
    @JsonProperty("merchant_txn_id")
    @ApiModelProperty(notes = "merchantTxnId of the transaction", required = true,
            example = "adsfjaklsdf8989")
    private String merchantTxnId;
}
