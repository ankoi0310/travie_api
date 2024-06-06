package vn.edu.hcmuaf.fit.travie_api.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import vn.edu.hcmuaf.fit.travie_api.core.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.dto.auth.*;

public interface AuthenticationService extends UserDetailsService {
    RegisterResponse register(RegisterRequest request) throws BaseException;

    String verify(String code) throws BaseException;

    LoginResponse login(LoginRequest loginRequest) throws BaseException;

    RefreshTokenResponse refreshToken() throws BaseException;

    void forgotPassword(String email) throws BaseException;

    void resetPassword(ResetPasswordRequest request) throws BaseException;
}
