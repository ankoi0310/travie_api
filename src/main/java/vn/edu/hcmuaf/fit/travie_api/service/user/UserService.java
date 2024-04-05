package vn.edu.hcmuaf.fit.travie_api.service.user;

import vn.edu.hcmuaf.fit.travie_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.dto.user.*;

public interface UserService {
    UserProfileDTO getProfile() throws BaseException;

    UserProfileDTO updateProfile(UserProfileUpdate userProfileUpdate) throws BaseException;

    void updateAvatar(String avatarUrl) throws BaseException;

    void changePassword(ChangePasswordRequest request) throws BaseException;
}
