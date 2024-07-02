package vn.edu.hcmuaf.fit.travie_api.service.impl;

import com.google.gson.Gson;
import com.restfb.*;
import com.restfb.types.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.hc.client5.http.fluent.Request;
import org.springframework.beans.factory.annotation.Value;
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
import vn.edu.hcmuaf.fit.travie_api.dto.user.AppUserGoogle;
import vn.edu.hcmuaf.fit.travie_api.entity.*;
import vn.edu.hcmuaf.fit.travie_api.repository.AppRoleRepository;
import vn.edu.hcmuaf.fit.travie_api.repository.UserRepository;
import vn.edu.hcmuaf.fit.travie_api.service.AuthenticationService;
import vn.edu.hcmuaf.fit.travie_api.service.OTPService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import static vn.edu.hcmuaf.fit.travie_api.core.shared.constants.AppConstant.DEFAULT_BASE_AVATAR_URL;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    @Value("${facebook.app-secret}")
    private String appSecret;

    private final UserRepository userRepository;
    private final AppRoleRepository appRoleRepository;

    private final JwtProvider jwtProvider;
    private final OTPService otpService;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void checkExistEmail(String email) throws BaseException {
        Optional<AppUser> appUser = userRepository.findByEmail(email);
        if (appUser.isPresent()) {
            throw new BadRequestException("Email đã được sử dụng");
        }
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) throws BaseException {
        try {
            Optional<AppUser> appUserEmail = userRepository.findByEmail(registerRequest.getEmail());
            if (appUserEmail.isPresent()) {
                throw new NotFoundException("Email đã được sử dụng");
            }

            Optional<AppUser> appUserPhone = userRepository.findByPhone(registerRequest.getPhone());
            if (appUserPhone.isPresent()) {
                throw new NotFoundException("Số điện thoại đã được sử dụng");
            }

            AppRole defaultAppRole = appRoleRepository.findByRole(AppConstant.DEFAULT_ROLE).orElse(null);
            if (defaultAppRole == null) {
                throw new ServiceUnavailableException("Không tìm thấy quyền mặc định");
            }

            AppUser newUser = AppUser.builder()
                                     .email(registerRequest.getEmail())
                                     .password(passwordEncoder.encode(registerRequest.getPassword()))
                                     .nickname(registerRequest.getNickname())
                                     .phone(registerRequest.getPhone())
                                     .gender(registerRequest.getGender())
                                     .birthday(registerRequest.getBirthday())
                                     .appRole(defaultAppRole)
                                     .build();

            System.out.println(registerRequest.getAvatar());
//            if (registerRequest.getAvatar() == null) {
            newUser.setAvatar(DEFAULT_BASE_AVATAR_URL + newUser.getNickname());
//            } else {
            // TODO: Save avatar to firebase storage and get url
//                System.out.println(registerRequest.getAvatar().getOriginalFilename());
//            }

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

        return switch (otp.getType()) {
            case VERIFY_EMAIL -> verifyEmail(otp);
            case RESET_PASSWORD -> verifyResetPassword(otp);
            case VERIFY_PHONE -> throw new BadRequestException("Chức năng chưa được hỗ trợ");
        };
    }

    private String verifyResetPassword(OTP otp) throws BaseException {
        userRepository.findByEmail(otp.getEmail())
                      .orElseThrow(() -> new NotFoundException("Người dùng không tồn tại"));
        return "Xác thực đặt lại mật khẩu thành công";
    }

    private String verifyEmail(OTP otp) throws BaseException {
        AppUser appUser = userRepository.findByEmail(otp.getEmail())
                                        .orElseThrow(() -> new NotFoundException("Tài khoản không tồn tại"));

        appUser.setEmailVerified(true);
        appUser.setEnabled(true);
        userRepository.save(appUser);
        return "Xác thực email thành công";
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
                                .nickname(appUser.getNickname())
                                .phone(appUser.getPhone())
                                .avatar(appUser.getAvatar())
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
    public LoginResponse loginFacebook(String accessToken) throws BaseException {
        try {
            User appUserFacebook = getFacebookProfile(accessToken);
            if (appUserFacebook == null) {
                throw new BadRequestException("Không thể lấy thông tin người dùng");
            }

            AppUser appUser = userRepository.findByFacebookId(appUserFacebook.getId()).orElse(null);
            if (appUser == null) {
                appUser = userRepository.findByEmail(appUserFacebook.getEmail()).orElse(null);
                if (appUser == null) {
                    appUser = new AppUser();
                    appUser.setEmail(appUserFacebook.getEmail());
                    appUser.setAccountNonLocked(true);
                    appUser.setEnabled(true);

                    AppRole defaultRole = appRoleRepository.findByRole(AppConstant.DEFAULT_ROLE).orElse(null);
                    if (defaultRole == null) {
                        throw new BadRequestException("Không tìm thấy role mặc định");
                    }

                    appUser.setAppRole(defaultRole);

                    appUser.setNickname(appUserFacebook.getName());
                    // appUser.getUserInfo().setIsMale(appUserFacebook.getGender().equalsIgnoreCase("male"));
                    appUser.setBirthday(LocalDate.from(appUserFacebook.getBirthdayAsDate().toInstant()));
                    appUser.setAvatar(appUserFacebook.getPicture().getUrl());

                }

                appUser.setFacebookId(appUserFacebook.getId());
                appUser.setFacebookConnected(true);
                appUser = userRepository.saveAndFlush(appUser);
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
                                .nickname(appUser.getNickname())
                                .phone(appUser.getPhone())
                                .avatar(appUser.getAvatar())
                                .accessToken(token)
                                .refreshToken(refreshToken)
                                .build();
        } catch (Exception e) {
            throw new BadRequestException("Đăng nhập không thành công");
        }
    }

    private User getFacebookProfile(String accessToken) {
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken, appSecret, Version.LATEST);
        return facebookClient.fetchObject("me", User.class, Parameter.with("fields", "id,name,email," +
                "first_name,last_name,picture.width(250).height(250),birthday,gender"));
    }

    @Override
    public LoginResponse loginGoogle(String accessToken) throws BaseException {
        try {
            AppUserGoogle appUserGoogle = getGoogleProfile(accessToken);
            if (appUserGoogle == null) {
                throw new BadRequestException("Không thể lấy thông tin người dùng");
            }

            AppUser appUser = userRepository.findByGoogleId(appUserGoogle.getSub()).orElse(null);
            if (appUser == null) {
                appUser = userRepository.findByEmail(appUserGoogle.getEmail()).orElse(null);
                if (appUser == null) {
                    appUser = new AppUser();
                    appUser.setEmail(appUserGoogle.getEmail());
                    appUser.setAccountNonLocked(true);
                    appUser.setEnabled(true);
                    appUser.setNickname(appUserGoogle.getName());
                    appUser.setAvatar(appUserGoogle.getPicture());

                    AppRole defaultRole = appRoleRepository.findByRole(AppConstant.DEFAULT_ROLE).orElse(null);
                    if (defaultRole == null) {
                        throw new BadRequestException("Không tìm thấy role mặc định");
                    }

                    appUser.setAppRole(defaultRole);

                    appUser.setNickname(appUserGoogle.getName());
                    appUser.setAvatar(appUserGoogle.getPicture());

                }

                appUser.setGoogleId(appUserGoogle.getSub());
                appUser.setGoogleConnected(true);
                appUser = userRepository.saveAndFlush(appUser);
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
                                .nickname(appUser.getNickname())
                                .phone(appUser.getPhone())
                                .avatar(appUser.getAvatar())
                                .accessToken(token)
                                .refreshToken(refreshToken)
                                .build();
        } catch (Exception e) {
            throw new BadRequestException("Đăng nhập không thành công");
        }
    }

    private AppUserGoogle getGoogleProfile(String accessToken) throws IOException {
        String link = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + accessToken;
        String response = Request.get(link).execute().returnContent().asString();
        return new Gson().fromJson(response, AppUserGoogle.class);
    }

    @Override
    public RefreshTokenResponse refreshToken(String token) throws BaseException {
        try {
            // Validate refresh token
            if (!jwtProvider.validateToken(token)) {
                throw new BadRequestException("Refresh token không hợp lệ");
            }

            // Extract username from refresh token
            String username = jwtProvider.getUsernameFromJWT(token);

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


    @Override
    public void forgotPassword(String email) throws BaseException {
        try {
            AppUser appUser = userRepository.findByEmail(email)
                                            .orElseThrow(() -> new NotFoundException("Email không tồn tại"));

            OTP otp = otpService.generateOTP(appUser.getEmail(), OTPType.RESET_PASSWORD);
            mailService.sendOTP(appUser.getEmail(), otp.getCode(), otp.getType());

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
            AppUser appUser = userRepository.findByEmail(request.getEmail())
                                            .orElseThrow(() -> new NotFoundException("Tài khoản không tồn tại"));

            appUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(appUser);
        } catch (NotFoundException e) {
            log.error(e.toString());
            throw e;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServiceUnavailableException("Không thể đặt lại mật khẩu");
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
