package io.mosip.manualVerificationService.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class AuthenticationRequest {

    private String id;
    private String version;
    private LocalDateTime requesttime;
    private Map<String, Object> metadata;
    private RequestData request;
}

