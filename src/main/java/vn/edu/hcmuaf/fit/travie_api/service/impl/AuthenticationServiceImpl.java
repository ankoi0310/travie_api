package vn.edu.hcmuaf.fit.travie_api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.fit.travie_api.core.exception.*;
import vn.edu.hcmuaf.fit.travie_api.core.infrastructure.jwt.JwtProvider;
import vn.edu.hcmuaf.fit.travie_api.core.infrastructure.mail.MailService;
import vn.edu.hcmuaf.fit.travie_api.core.shared.constants.AppConstant;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.otp.OTPType;
import vn.edu.hcmuaf.fit.travie_api.dto.auth.*;
import vn.edu.hcmuaf.fit.travie_api.entity.*;
import vn.edu.hcmuaf.fit.travie_api.repository.AppRoleRepository;
import vn.edu.hcmuaf.fit.travie_api.repository.UserRepository;
import vn.edu.hcmuaf.fit.travie_api.service.AuthenticationService;
import vn.edu.hcmuaf.fit.travie_api.service.OTPService;

import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final AppRoleRepository appRoleRepository;

    private final JwtProvider jwtProvider;
    private final OTPService otpService;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) throws BaseException {
        try {
            Optional<AppUser> appUserEmail = userRepository.findByEmail(registerRequest.getEmail());
            if (appUserEmail.isPresent()) {
                throw new NotFoundException("Email đã tồn tại");
            }

            Optional<AppUser> appUserPhone = userRepository.findByPhone(registerRequest.getPhone());

            if (appUserPhone.isPresent()) {
                throw new NotFoundException("Số điện thoại đã tồn tại");
            }

            AppRole defaultAppRole = appRoleRepository.findByRole(AppConstant.DEFAULT_ROLE).orElse(null);
            if (defaultAppRole == null) {
                throw new ServiceUnavailableException("Không tìm thấy quyền mặc định");
            }
            AppRole defaultAppRole1;
            synchronized (this) {
                defaultAppRole1 = appRoleRepository.findByRole(AppConstant.DEFAULT_ROLE)
                        .orElseGet(() -> {
                            AppRole newRole = AppRole.builder()
                                    .role(AppConstant.DEFAULT_ROLE)
                                    .build();
                            return appRoleRepository.save(newRole);
                        });
            }

            UserInfo userInfo = UserInfo.builder()
                                        .fullName(registerRequest.getFullName())
                                        .build();
            AppUser newUser = AppUser.builder()
                                     .email(registerRequest.getEmail())
                                     .phone(registerRequest.getPhone())
                                     .password(passwordEncoder.encode(registerRequest.getPassword()))
                                     .userInfo(userInfo)
                                     .appRole(defaultAppRole1)
                                     .build();

            userRepository.save(newUser);

            // Create OTP
            OTP otp = otpService.generateOTP(newUser.getEmail(), OTPType.VERIFY_EMAIL);

            // Send OTP
            mailService.sendOTP(newUser.getEmail(), otp.getCode(), otp.getType());

            return RegisterResponse.builder()
                                   .email(newUser.getEmail())
                                   .build();
        } catch (NotFoundException | ServiceUnavailableException e) {
            log.error(e.toString());
            throw e;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServiceUnavailableException("Không thể tạo tài khoản mới");
        }
    }

    @Override
    public String verify(String code) throws BaseException {
        OTP otp = otpService.getOTPByCode(code);

        if (otp == null) {
            throw new BadRequestException("Mã OTP không hợp lệ");
        }

        switch (otp.getType()) {
            case VERIFY_EMAIL:
                AppUser appUser = userRepository.findByEmail(otp.getEmail())
                                                .orElseThrow(() -> new NotFoundException("Tài khoản không tồn tại"));

                appUser.setEmailVerified(true);
                appUser.setEnabled(true);
                userRepository.save(appUser);
                return "Xác thực email thành công";
            case VERIFY_PHONE, RESET_PASSWORD:
                throw new BadRequestException("Chức năng chưa được hỗ trợ");
            default:
                throw new BadRequestException("Loại OTP không hợp lệ");
        }
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) throws BaseException {
        try {
            AppUser appUser = userRepository.findByUsername(loginRequest.getUsername())
                                            .orElseThrow(() -> new NotFoundException("Tài khoản không tồn tại"));

            if (!passwordEncoder.matches(loginRequest.getPassword(), appUser.getPassword())) {
                throw new BadRequestException("Mật khẩu không đúng");
            }

            if (!appUser.isEnabled()) {
                throw new BadRequestException("Tài khoản chưa được kích hoạt");
            }

            // Create authentication
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    appUser,
                    null,
                    appUser.getAuthorities()
            );

            // Generate token
            String token = jwtProvider.generateAccessToken(authentication);

            // Generate refresh token
            String refreshToken = jwtProvider.generateRefreshToken(authentication);

            return LoginResponse.builder()
                                .welcomeName(StringUtils.substringAfterLast(appUser.getUserInfo().getFullName(), " "))
                                .accessToken(token)
                                .refreshToken(refreshToken)
                                .build();
        } catch (NotFoundException | BadRequestException e) {
            log.error(e.toString());
            throw e;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServiceUnavailableException("Không thể đăng nhập");
        }
    }

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) throws BaseException {
        try {
            String refreshToken = request.getRefreshToken();

            // Validate refresh token
            if (!jwtProvider.validateToken(refreshToken)) {
                throw new BadRequestException("Refresh token không hợp lệ");
            }

            // Extract username from refresh token
            String username = jwtProvider.getUsernameFromJWT(refreshToken);

            // Load user details
            UserDetails userDetails = loadUserByUsername(username);

            // Create new authentication
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            // Generate new access token
            String newAccessToken = jwtProvider.generateAccessToken(authentication);

            // Generate new refresh token
            String newRefreshToken = jwtProvider.generateRefreshToken(authentication);

            return RefreshTokenResponse.builder()
                                       .accessToken(newAccessToken)
                                       .refreshToken(newRefreshToken)
                                       .build();
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServiceUnavailableException("Không thể làm mới token");
        }
    }

    private String resetPasswordEmail;
    @Override
    public void forgotPassword(String email) throws BaseException {
        try {
            AppUser appUser = userRepository.findByEmail(email)
                    .orElseThrow(() -> new NotFoundException("Email không tồn tại"));

            OTP otp = otpService.generateOTP(appUser.getEmail(), OTPType.RESET_PASSWORD);
            mailService.sendOTP(appUser.getEmail(), otp.getCode(), otp.getType());

            resetPasswordEmail = email;
        } catch (NotFoundException e) {
            log.error(e.toString());
            throw e;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServiceUnavailableException("Không thể gửi OTP, vui lòng thử lại sau");
        }
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) throws BaseException {
        try {
            OTP otp = otpService.getOTPByCode(request.getOtpCode());

            if (otp == null || !otp.getEmail().equals(resetPasswordEmail) || !otp.getType().equals(OTPType.RESET_PASSWORD)) {
                throw new BadRequestException("Mã OTP không hợp lệ");
            }
            AppUser appUser = userRepository.findByEmail(resetPasswordEmail)
                    .orElseThrow(() -> new NotFoundException("Người dùng không tồn tại"));

            String newPassword = passwordEncoder.encode(request.getNewPassword());
            appUser.setPassword(newPassword);

            userRepository.save(appUser);

            resetPasswordEmail = null;

        } catch (NotFoundException | BadRequestException e) {
            log.error(e.toString());
            throw e;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServiceUnavailableException("Không thể đặt lại mật khẩu, vui lòng thử lại sau");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> appUser = userRepository.findByUsername(username);
        if (appUser.isEmpty()) {
            throw new UsernameNotFoundException("Không tìm thấy tài khoản");
        }

        return appUser.get();
    }
}
