package vn.edu.hcmuaf.fit.travie_api.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.hcmuaf.fit.travie_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.core.handler.exception.NotFoundException;
import vn.edu.hcmuaf.fit.travie_api.core.shared.utils.AppUtil;
import vn.edu.hcmuaf.fit.travie_api.dto.user.UserProfileDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.user.UserProfileUpdate;
import vn.edu.hcmuaf.fit.travie_api.entity.AppUser;
import vn.edu.hcmuaf.fit.travie_api.mapper.UserMapper;
import vn.edu.hcmuaf.fit.travie_api.repository.user.UserRepository;

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
            String email = AppUtil.getCurrentEmail();
            return userRepository.findByEmail(email)
                                 .map(userMapper::toProfileDTO)
                                 .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin người dùng!"));
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException("Lỗi không xác định!");
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
            throw new BaseException("Lỗi không xác định!");
        }
    }

    @Override
    public void updateAvatar(MultipartFile avatar) throws BaseException {
        try {
            String email = AppUtil.getCurrentEmail();
            AppUser user = userRepository.findByEmail(email)
                                         .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin người " +
                                                 "dùng!"));

            userRepository.save(user);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException("Lỗi không xác định!");
        }
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) throws BaseException {
        try {
            String email = AppUtil.getCurrentEmail();


        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException("Lỗi không xác định!");
        }
    }
}
