package io.mosip.manualVerificationService.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RequestData {
    @NotNull
    private String userName;

    @NotNull
    private String password;

    @NotNull
    private String appId;

    @NotNull
    private String clientId;

    @NotNull
    private String clientSecret;
}
