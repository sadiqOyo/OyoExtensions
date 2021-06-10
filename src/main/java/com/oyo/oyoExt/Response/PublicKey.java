package com.oyo.oyoExt.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class PublicKey {

    @JsonProperty("encryption_version")
    private String version;

    private String key;
}
