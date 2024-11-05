package io.mosip.manualVerificationService.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class JWTDecoder {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static JsonNode decodeJWTPayload(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid JWT token format.");
            }
            String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);

            return objectMapper.readTree(payloadJson);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
