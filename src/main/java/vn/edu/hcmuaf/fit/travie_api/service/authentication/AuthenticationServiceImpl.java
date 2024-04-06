package vn.edu.hcmuaf.fit.travie_api.service.authentication;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.fit.travie_api.core.handler.exception.*;
import vn.edu.hcmuaf.fit.travie_api.core.infrastructure.jwt.JwtProvider;
import vn.edu.hcmuaf.fit.travie_api.core.shared.constants.AppConstant;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.OTPType;
import vn.edu.hcmuaf.fit.travie_api.dto.auth.*;
import vn.edu.hcmuaf.fit.travie_api.entity.*;
import vn.edu.hcmuaf.fit.travie_api.repository.approle.AppRoleRepository;
import vn.edu.hcmuaf.fit.travie_api.repository.otp.OtpInfoRepository;
import vn.edu.hcmuaf.fit.travie_api.repository.user.UserRepository;

import java.util.Optional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final AppRoleRepository appRoleRepository;

    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final OtpInfoRepository otpInfoRepository;

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

            AppRole defaultAppRole = appRoleRepository.findByRole(AppConstant.DEFAULT_ROLE)
                                                      .orElseThrow(() -> new ServiceUnavailableException("Không tìm " +
                                                              "thấy quyền mặc định"));

            UserInfo newUserInfo = UserInfo.builder()
                                           .fullName(registerRequest.getFullName())
                                           .gender(registerRequest.getGender())
                                           .birthday(registerRequest.getBirthday())
                                           .build();

            AppUser newUser = AppUser.builder()
                                     .email(registerRequest.getEmail())
                                     .phone(registerRequest.getPhone())
                                     .password(passwordEncoder.encode(registerRequest.getPassword()))
                                     .userInfo(newUserInfo)
                                     .appRole(defaultAppRole)
                                     .build();

            System.out.println(newUser.toString());

            userRepository.save(newUser);

            return RegisterResponse.builder()
                                   .fullName(newUser.getUserInfo().getFullName())
                                   .email(newUser.getEmail())
                                   .build();
        } catch (NotFoundException | ServiceUnavailableException e) {
            log.error(e.toString());
            throw e;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServiceBusinessException("Không thể tạo tài khoản mới");
        }
    }

    @Override
    public void verify(String otp) throws BaseException {
        try {
            OtpInfo otpInfo = otpInfoRepository.findByOtp(otp)
                                               .orElseThrow(() -> new BadRequestException("OTP không hợp lệ"));

            if (otpInfo.getType() != OTPType.VERIFICATION) {
                throw new BadRequestException("Loại OTP không hợp lệ");
            }

            AppUser appUser = userRepository.findByEmail(otpInfo.getEmail())
                                            .orElseThrow(() -> new NotFoundException("Tài khoản không tồn tại"));

            appUser.setEnabled(true);
            appUser.setEmailVerified(true);
            userRepository.save(appUser);

            otpInfoRepository.delete(otpInfo);
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServiceBusinessException("Không thể xác thực OTP");
        }
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) throws BaseException {
        AppUser appUser = userRepository.findByUsername(loginRequest.getUsername())
                                        .orElseThrow(() -> new NotFoundException("Tài khoản không tồn tại"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), appUser.getPassword())) {
            throw new BadRequestException("Mật khẩu không đúng");
        }

        if (!appUser.isEnabled()) {
            throw new BadRequestException("Tài khoản chưa được kích hoạt");
        }

        // Generate token
        String token = jwtProvider.generateToken(appUser);

        return LoginResponse.builder()
                            .fullName(appUser.getUserInfo().getFullName())
                            .email(appUser.getEmail())
                            .accessToken(token)
                            .build();
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) throws BaseException {
        try {
            OtpInfo otpInfo = otpInfoRepository.findByOtp(request.getOtp())
                                               .orElseThrow(() -> new BadRequestException(
                                                       "OTP không hợp lệ"));

            AppUser appUser = userRepository.findByEmail(otpInfo.getEmail())
                                            .orElseThrow(() -> new NotFoundException("Tài khoản không tồn tại"));

            appUser.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(appUser);

            otpInfoRepository.delete(otpInfo);
        } catch (NotFoundException | BadRequestException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServiceBusinessException("Không thể đặt lại mật khẩu");
        }
    }

    @Override
    public String refreshToken(String token) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> appUser = userRepository.findByEmail(username);
        if (appUser.isEmpty()) {
            throw new UsernameNotFoundException("Không tìm thấy tài khoản");
        }

        return appUser.get();
    }
}
