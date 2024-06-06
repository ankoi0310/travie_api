package vn.edu.hcmuaf.fit.travie_api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.fit.travie_api.core.exception.*;
import vn.edu.hcmuaf.fit.travie_api.core.infrastructure.jwt.JwtProvider;
import vn.edu.hcmuaf.fit.travie_api.core.shared.constants.AppConstant;
import vn.edu.hcmuaf.fit.travie_api.dto.auth.*;
import vn.edu.hcmuaf.fit.travie_api.entity.*;
import vn.edu.hcmuaf.fit.travie_api.mapper.UserMapper;
import vn.edu.hcmuaf.fit.travie_api.repository.AppRoleRepository;
import vn.edu.hcmuaf.fit.travie_api.repository.UserRepository;
import vn.edu.hcmuaf.fit.travie_api.service.AuthenticationService;

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
//    private final MailService mailService;

    private final UserMapper userMapper;
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

            // TODO: Send email verification

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
                            .fullName(appUser.getUserInfo().getFullName())
                            .email(appUser.getEmail())
                            .accessToken(token)
                            .build();
    }

    @Override
    public void forgotPassword(String email) throws BaseException {
        try {
            AppUser appUser = userRepository.findByEmail(email)
                                            .orElseThrow(() -> new NotFoundException("Không tìm thấy tài khoản với " +
                                                    "email này"));

            // create verification token
            UUID token = UUID.randomUUID();
            LocalDateTime expiredDate = LocalDateTime.now().plusMinutes(AppConstant.RESET_PASSWORD_TOKEN_EXPIRED_DATE);
        } catch (NotFoundException e) {
            log.error(e.toString());
            throw e;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServiceUnavailableException("Không thể gửi yêu cầu đặt lại mật khẩu");
        }
    }

    @Override
    public void resetPassword(UUID token, String newPassword) throws BaseException {

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
