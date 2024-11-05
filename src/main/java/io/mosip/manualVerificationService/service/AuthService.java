package io.mosip.manualVerificationService.service;

import io.mosip.manualVerificationService.dto.*;

public interface AuthService {

    AuthenticationResponse loginClient (LoginRequest loginRequest) ;

    UserResponse fetchUsersByRole (String role) ;

    UserAttributes fetchUserAttributes () ;
}
