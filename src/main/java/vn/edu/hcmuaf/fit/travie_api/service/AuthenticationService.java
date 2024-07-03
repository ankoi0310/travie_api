package vn.edu.hcmuaf.fit.travie_api.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.hcmuaf.fit.travie_api.core.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.dto.auth.*;

public interface AuthenticationService extends UserDetailsService {
    void checkExistEmail(String email) throws BaseException;

    RegisterResponse register(MultipartFile avatar, RegisterRequest request) throws BaseException;

    String verify(String code) throws BaseException;

    LoginResponse login(LoginRequest loginRequest) throws BaseException;

    LoginResponse loginFacebook(String accessToken) throws BaseException;

    LoginResponse loginGoogle(String accessToken) throws BaseException;

    RefreshTokenResponse refreshToken(String token) throws BaseException;

    void forgotPassword(String email) throws BaseException;

    void resetPassword(ResetPasswordRequest request) throws BaseException;
}
