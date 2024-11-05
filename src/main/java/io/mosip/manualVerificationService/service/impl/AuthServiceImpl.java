package io.mosip.manualVerificationService.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import io.mosip.manualVerificationService.dto.*;
import io.mosip.manualVerificationService.service.AuthService;
import io.mosip.manualVerificationService.utils.AuthTokenManager;
import io.mosip.manualVerificationService.utils.JWTDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;

@Service
public class AuthServiceImpl implements AuthService {

    @Value("${mosip.authManager.authUrl}")
    private String authenticationUrl;

    @Value("${mosip.authManager.getUsersUrl}")
    private String fetchUsersUrl;

    @Value("${mosip.manualVerificationService.appId}")
    private String appId;

    @Value("${mosip.manualVerificationService.clientId}")
    private String clientId;

    @Value("${mosip.manualVerificationService.clientSecret}")
    private String clientSecret;

    @Autowired
    private AuthTokenManager authTokenManager;

    @Override
    public AuthenticationResponse loginClient(LoginRequest loginRequest) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // Build the RequestData object
        RequestData requestData = new RequestData();
        requestData.setUserName(loginRequest.getUserName());
        requestData.setPassword(loginRequest.getPassword());
        requestData.setAppId(appId);
        requestData.setClientId(clientId);
        requestData.setClientSecret(clientSecret);

        // Build the AuthenticationRequest object
        AuthenticationRequest authRequest = new AuthenticationRequest();
        authRequest.setId("");
        authRequest.setVersion("");
        authRequest.setRequesttime(LocalDateTime.now());
        authRequest.setMetadata(new HashMap<>());
        authRequest.setRequest(requestData);

        HttpEntity<AuthenticationRequest> entity = new HttpEntity<>(authRequest, headers);

        ResponseEntity<AuthenticationResponse> response = restTemplate.exchange(
                authenticationUrl,
                HttpMethod.POST,
                entity,
                AuthenticationResponse.class
        );

        authTokenManager.setToken(Objects.requireNonNull(response.getBody()).getAuthToken());

        return response.getBody();
    }

    @Override
    public UserResponse fetchUsersByRole(String role) {
        RestTemplate restTemplate = new RestTemplate();

        String url = String.format(fetchUsersUrl + "?roleName=" + role);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Cookie", "Authorization=" + authTokenManager.getToken());

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<UserResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                UserResponse.class
        );

        return response.getBody();
    }

    @Override
    public UserAttributes fetchUserAttributes() {
        UserAttributes userAttributes =  new UserAttributes();

        // Decode jwt token to fetch attributes
        JsonNode payload = JWTDecoder.decodeJWTPayload(authTokenManager.getToken());

        if (payload != null) {
            String orgName = payload.has("organizationName") ? payload.get("organizationName").asText() : null;
            String city = payload.has("city") ? payload.get("city").asText() : null;

            userAttributes.setCity(city);
            userAttributes.setOrganizationName(orgName);

            return userAttributes;
        }
        else {
            return null;
        }
    }



}