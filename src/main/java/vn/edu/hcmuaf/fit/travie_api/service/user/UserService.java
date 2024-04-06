package vn.edu.hcmuaf.fit.travie_api.service.user;

import org.springframework.web.multipart.MultipartFile;
import vn.edu.hcmuaf.fit.travie_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.dto.user.*;

public interface UserService {
    AppUserDTO getUserByEmail(String email) throws BaseException;

    UserProfileDTO getProfile() throws BaseException;

    UserProfileDTO updateProfile(UserProfileUpdate userProfileUpdate) throws BaseException;

    void updateAvatar(MultipartFile avatar) throws BaseException;

    void changePassword(String oldPassword, String newPassword) throws BaseException;
}
