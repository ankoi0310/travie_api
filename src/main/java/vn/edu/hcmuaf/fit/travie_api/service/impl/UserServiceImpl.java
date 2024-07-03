package vn.edu.hcmuaf.fit.travie_api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.hcmuaf.fit.travie_api.core.exception.*;
import vn.edu.hcmuaf.fit.travie_api.core.infrastructure.firebase.FirebaseService;
import vn.edu.hcmuaf.fit.travie_api.core.shared.utils.AppUtil;
import vn.edu.hcmuaf.fit.travie_api.dto.auth.ChangePasswordRequest;
import vn.edu.hcmuaf.fit.travie_api.dto.invoice.InvoiceDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.user.UserProfileDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.user.UserProfileRequest;
import vn.edu.hcmuaf.fit.travie_api.entity.AppUser;
import vn.edu.hcmuaf.fit.travie_api.mapper.UserMapper;
import vn.edu.hcmuaf.fit.travie_api.repository.UserRepository;
import vn.edu.hcmuaf.fit.travie_api.service.InvoiceService;
import vn.edu.hcmuaf.fit.travie_api.service.UserService;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    private final InvoiceService invoiceService;
    private final FirebaseService firebaseService;

    @Override
    public UserProfileDTO getProfile() throws BaseException {
        try {
            String username = AppUtil.getCurrentUsername();

            AppUser user = userRepository.findByUsername(username)
                                         .orElseThrow(() -> new NotFoundException("Người dùng không tồn tại!"));

            return userMapper.toUserProfileDTO(user);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BaseException("Lỗi khi lấy thông tin người dùng!");
        }
    }

    @Override
    public UserProfileDTO updateProfile(UserProfileRequest request) throws BaseException {
        try {
            String username = AppUtil.getCurrentUsername();

            AppUser user = userRepository.findByUsername(username)
                                         .orElseThrow(() -> new NotFoundException("Người dùng không tồn tại!"));

            if (!user.isPhoneVerified() && !request.getPhone().equals(user.getPhone())) {
                user.setPhone(request.getPhone());
            }

            Optional<AppUser> userOptionalByNickname = userRepository.findByNickname(request.getNickname());
            if (userOptionalByNickname.isPresent()) {
                AppUser userByNickname = userOptionalByNickname.get();
                if (request.getNickname().equals(user.getNickname()) && userByNickname.getId() != user.getId()) {
                    throw new BadRequestException("Nickname đã tồn tại!");
                }
            }

            user.setNickname(request.getNickname());
            user.setGender(request.getGender());
            user.setBirthday(request.getBirthday());

            userRepository.save(user);

            return userMapper.toUserProfileDTO(user);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException("Lỗi khi cập nhật thông tin người dùng!");
        }
    }

    @Override
    public void updateAvatar(MultipartFile avatar) throws BaseException {
        try {
            String username = AppUtil.getCurrentUsername();
            AppUser user = userRepository.findByUsername(username)
                                         .orElseThrow(() -> new NotFoundException("Không tìm thấy tài khoản người " +
                                                 "dùng!"));

            if (avatar != null) {
                String filename = avatar.getOriginalFilename();
                File file = firebaseService.convertToFile(avatar, filename);
                String URL = firebaseService.uploadFile(file, filename);
                file.delete();
                user.setAvatar(URL);
            }

            userRepository.save(user);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException("Lỗi khi cập nhật ảnh đại diện!");
        }
    }

    @Override
    public void changePassword(ChangePasswordRequest request) throws BaseException {
        String username = AppUtil.getCurrentUsername();
        AppUser user = userRepository.findByUsername(username)
                                     .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin người " +
                                             "dùng!"));

        if (request.getCurrentPassword().equals(request.getNewPassword())) {
            throw new BadRequestException("Mật khẩu mới không được trùng với mật khẩu cũ!");
        }

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new BadRequestException("Mật khẩu cũ không đúng!");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public List<InvoiceDTO> getBookingHistory() throws BaseException {
        String username = AppUtil.getCurrentUsername();
        AppUser user = userRepository.findByUsername(username)
                                     .orElseThrow(() -> new NotFoundException("Người dùng không tồn tại!"));

        return invoiceService.getInvoicesByUser(user);
    }
}
