package vn.edu.hcmuaf.fit.travie_api.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.fit.travie_api.core.handler.exception.*;
import vn.edu.hcmuaf.fit.travie_api.core.infrastructure.media.FileService;
import vn.edu.hcmuaf.fit.travie_api.core.shared.utils.AppUtil;
import vn.edu.hcmuaf.fit.travie_api.dto.user.*;
import vn.edu.hcmuaf.fit.travie_api.entity.AppUser;
import vn.edu.hcmuaf.fit.travie_api.mapper.UserMapper;
import vn.edu.hcmuaf.fit.travie_api.repository.user.UserRepository;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final FileService fileService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserProfileDTO getProfile() throws BaseException {
        try {
            String email = AppUtil.getCurrentEmail();
            return userRepository.findByEmail(email)
                                 .map(userMapper::toProfileDTO)
                                 .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin người dùng!"));
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServiceBusinessException("Không thể lấy thông tin người dùng!");
        }
    }

    @Override
    public UserProfileDTO updateProfile(UserProfileUpdate userProfileUpdate) throws BaseException {
        try {
            String email = AppUtil.getCurrentEmail();
            AppUser user = userRepository.findByEmail(email)
                                         .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin người " +
                                                 "dùng!"));

            user.getUserInfo().setFullName(userProfileUpdate.getFullName());
            user.getUserInfo().setGender(userProfileUpdate.getGender());
            user.getUserInfo().setBirthday(userProfileUpdate.getBirthday());

            userRepository.save(user);
            return userMapper.toProfileDTO(user);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServiceBusinessException("Không thể cập nhật thông tin người dùng!");
        }
    }

    @Override
    public void updateAvatar(String avatar) throws BaseException {
        try {
            String email = AppUtil.getCurrentEmail();
            AppUser user = userRepository.findByEmail(email)
                                         .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin người " +
                                                 "dùng!"));

            // delete old avatar
            if (user.getUserInfo().getAvatar() != null) {
                fileService.removeFromFirebaseStorage(user.getUserInfo().getAvatar());
            }

            user.getUserInfo().setAvatar(avatar);

            userRepository.save(user);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServiceBusinessException("Không thể cập nhật ảnh đại diện!");
        }
    }

    @Override
    public void changePassword(ChangePasswordRequest request) throws BaseException {
        try {
            String email = AppUtil.getCurrentEmail();
            AppUser user = userRepository.findByEmail(email)
                                         .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin người " +
                                                 "dùng!"));

            if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
                throw new BadRequestException("Mật khẩu hiện tại không đúng!");
            }

            user.setPassword(passwordEncoder.encode(request.getNewPassword()));

            userRepository.save(user);

        } catch (NotFoundException | BadRequestException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServiceBusinessException("Không thể thay đổi mật khẩu!");
        }
    }
}
