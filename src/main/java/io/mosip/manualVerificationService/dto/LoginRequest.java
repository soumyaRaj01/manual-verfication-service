package io.mosip.manualVerificationService.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginRequest {

    @NotNull
    private String userName;

    @NotNull
    private String password;
}
