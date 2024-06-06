package vn.edu.hcmuaf.fit.travie_api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.hcmuaf.fit.travie_api.core.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.core.exception.NotFoundException;
import vn.edu.hcmuaf.fit.travie_api.core.shared.utils.AppUtil;
import vn.edu.hcmuaf.fit.travie_api.dto.user.UserProfileDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.user.UserProfileRequest;
import vn.edu.hcmuaf.fit.travie_api.entity.AppUser;
import vn.edu.hcmuaf.fit.travie_api.mapper.UserMapper;
import vn.edu.hcmuaf.fit.travie_api.repository.UserRepository;
import vn.edu.hcmuaf.fit.travie_api.service.UserService;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Value("${firebase.storage.bucket}")
    private String firebaseStorageBucket;

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserProfileDTO getProfile() throws BaseException {
        try {
            String email = AppUtil.getCurrentUsername();

            AppUser user = userRepository.findByEmail(email)
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
            String email = AppUtil.getCurrentUsername();

            AppUser user = userRepository.findByEmail(email)
                                         .orElseThrow(() -> new NotFoundException("Người dùng không tồn tại!"));

            if (!user.isPhoneVerified() && !request.getPhone().equals(user.getPhone())) {
                user.setPhone(request.getPhone());
            }

            if (!request.getFullName().equals(user.getUserInfo().getFullName())) {
                user.getUserInfo().setFullName(request.getFullName());
            }

            userRepository.save(user);

            return userMapper.toUserProfileDTO(user);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BaseException("Lỗi khi cập nhật thông tin người dùng!");
        }
    }

    @Override
    public void updateAvatar(MultipartFile avatar) throws BaseException {
        String email = AppUtil.getCurrentUsername();
        AppUser user = userRepository.findByEmail(email)
                                     .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin người " +
                                             "dùng!"));

        userRepository.save(user);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) throws BaseException {
        String email = AppUtil.getCurrentUsername();


    }
}
