package com.oyo.oyoExt.Request;


import com.oyo.paymentgatewayscommon.request.AmountRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Currency;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Products implements  Serializable{
    private String description;
    private String name;
    private Double amount;

}
