package io.mosip.manualVerificationService.controller;

import io.mosip.manualVerificationService.dto.*;
import io.mosip.manualVerificationService.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public AuthenticationResponse authenticate(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.loginClient(loginRequest);
    }

    @RequestMapping(value = "/userdetails", method = RequestMethod.GET)
    public ResponseEntity<UserResponse> fetchUsersByRole (@RequestParam String role) {
        UserResponse userResponse = authService.fetchUsersByRole(role);
        return ResponseEntity.ok(userResponse);
    }

    @RequestMapping(value = "/userdetails/attributes", method = RequestMethod.GET)
    public ResponseEntity<UserAttributes> fetchUserAttributes () {
        UserAttributes userAttributes = authService.fetchUserAttributes();
        return ResponseEntity.ok(userAttributes);
    }

}
