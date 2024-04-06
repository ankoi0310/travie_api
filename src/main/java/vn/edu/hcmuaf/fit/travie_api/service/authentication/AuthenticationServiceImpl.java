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
import vn.edu.hcmuaf.fit.travie_api.dto.auth.*;
import vn.edu.hcmuaf.fit.travie_api.entity.*;
import vn.edu.hcmuaf.fit.travie_api.repository.approle.AppRoleRepository;
import vn.edu.hcmuaf.fit.travie_api.repository.resetpasswordtoken.ResetPasswordTokenRepository;
import vn.edu.hcmuaf.fit.travie_api.repository.user.UserRepository;
import vn.edu.hcmuaf.fit.travie_api.repository.verificationtoken.VerificationTokenRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final AppRoleRepository appRoleRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final ResetPasswordTokenRepository resetPasswordTokenRepository;
//    private final MailService mailService;

    private final JwtProvider jwtProvider;
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

            UserInfo userInfo = UserInfo.builder()
                                        .fullName(registerRequest.getFullName())
                                        .birthday(registerRequest.getBirthday())
                                        .build();
            AppUser newUser = AppUser.builder()
                                     .email(registerRequest.getEmail())
                                     .phone(registerRequest.getPhone())
                                     .password(passwordEncoder.encode(registerRequest.getPassword()))
                                     .userInfo(userInfo)
                                     .appRole(defaultAppRole)
                                     .build();

            userRepository.save(newUser);

            // create verification token
            UUID token = UUID.randomUUID();
            LocalDateTime expiredDate = LocalDateTime.now().plusDays(AppConstant.VERIFICATION_TOKEN_EXPIRED_DATE);
            VerificationToken verificationToken = VerificationToken.builder()
                                                                   .token(token)
                                                                   .email(newUser.getEmail())
                                                                   .expiredDate(expiredDate)
                                                                   .build();

            // TODO: Send email verification

            verificationTokenRepository.save(verificationToken);

            return RegisterResponse.builder()
                                   .username(newUser.getUsername())
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
    public void verifyToken(UUID token) throws BaseException {
        try {
            VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                                                                             .orElseThrow(() -> new BadRequestException(
                                                                                     "Token không hợp lệ"));

            if (verificationToken.getExpiredDate().isBefore(LocalDateTime.now())) {
                throw new BadRequestException("Token đã hết hạn");
            }

            AppUser appUser = userRepository.findByEmail(verificationToken.getEmail())
                                            .orElseThrow(() -> new NotFoundException("Tài khoản không tồn tại"));

            appUser.setEnabled(true);
            appUser.setEmailVerified(true);
            userRepository.save(appUser);

            verificationTokenRepository.delete(verificationToken);
        } catch (NotFoundException | BadRequestException e) {
            log.error(e.toString());
            throw e;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServiceUnavailableException("Không thể xác thực tài khoản");
        }
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) throws BaseException {
        AppUser appUser = userRepository.findByUsername(loginRequest.getUsername())
                                        .orElseThrow(() -> new NotFoundException("Tài khoản không tồn tại"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), appUser.getPassword())) {
            throw new BadRequestException("Mật khẩu không đúng");
        }

        if (!appUser.getEnabled()) {
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
    public void forgetPassword(String email) throws BaseException {
        try {
            AppUser appUser = userRepository.findByEmail(email)
                                            .orElseThrow(() -> new NotFoundException("Không tìm thấy tài khoản với " +
                                                    "email này"));

            // create verification token
            UUID token = UUID.randomUUID();
            LocalDateTime expiredDate = LocalDateTime.now().plusMinutes(AppConstant.RESET_PASSWORD_TOKEN_EXPIRED_DATE);
            ResetPasswordToken resetPasswordToken = ResetPasswordToken.builder()
                                                                      .token(token)
                                                                      .email(appUser.getEmail())
                                                                      .expiredDate(expiredDate)
                                                                      .build();

            resetPasswordTokenRepository.save(resetPasswordToken);
        } catch (NotFoundException e) {
            log.error(e.toString());
            throw e;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServiceUnavailableException("Không thể gửi yêu cầu đặt lại mật khẩu");
        }
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) throws BaseException {
        try {
            ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findByToken(request.getToken())
                                                                                .orElseThrow(() -> new BadRequestException(
                                                                                        "Token không hợp lệ"));

            if (resetPasswordToken.getExpiredDate().isBefore(LocalDateTime.now())) {
                throw new BadRequestException("Token đã hết hạn");
            }

            AppUser appUser = userRepository.findByEmail(resetPasswordToken.getEmail())
                                            .orElseThrow(() -> new NotFoundException("Tài khoản không tồn tại"));

            appUser.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(appUser);

            resetPasswordTokenRepository.delete(resetPasswordToken);
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
