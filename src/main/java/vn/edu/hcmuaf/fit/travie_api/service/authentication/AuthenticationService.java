package vn.edu.hcmuaf.fit.travie_api.service.authentication;

import org.springframework.security.core.userdetails.UserDetailsService;
import vn.edu.hcmuaf.fit.travie_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.dto.auth.*;

public interface AuthenticationService extends UserDetailsService {
    RegisterResponse register(RegisterRequest registerRequest) throws BaseException;

    void verify(String otp) throws BaseException;

    LoginResponse login(LoginRequest loginRequest) throws BaseException;

    void resetPassword(ResetPasswordRequest request) throws BaseException;

    String refreshToken(String token) throws BaseException;
}
