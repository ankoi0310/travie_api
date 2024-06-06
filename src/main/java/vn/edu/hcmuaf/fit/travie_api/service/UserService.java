package vn.edu.hcmuaf.fit.travie_api.service;

import org.springframework.web.multipart.MultipartFile;
import vn.edu.hcmuaf.fit.travie_api.core.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.dto.user.UserProfileDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.user.UserProfileUpdate;

public interface UserService {
    UserProfileDTO getProfile() throws BaseException;

    UserProfileDTO updateProfile(UserProfileUpdate userProfileUpdate) throws BaseException;

    void updateAvatar(MultipartFile avatar) throws BaseException;

    void changePassword(String oldPassword, String newPassword) throws BaseException;
}
