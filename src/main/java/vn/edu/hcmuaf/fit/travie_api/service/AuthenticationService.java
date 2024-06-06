package vn.edu.hcmuaf.fit.travie_api.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import vn.edu.hcmuaf.fit.travie_api.core.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.dto.auth.*;

import java.util.UUID;

public interface AuthenticationService extends UserDetailsService {
    RegisterResponse register(RegisterRequest registerRequest) throws BaseException;

    LoginResponse login(LoginRequest loginRequest) throws BaseException;

    void verifyToken(UUID token) throws BaseException;

    void forgotPassword(String email) throws BaseException;

    void resetPassword(UUID token, String newPassword) throws BaseException;

    String refreshToken(String token) throws BaseException;
}
