package vn.edu.hcmuaf.fit.travie_api.mapper;

import org.mapstruct.Mapper;
import vn.edu.hcmuaf.fit.travie_api.dto.user.UserProfileDTO;
import vn.edu.hcmuaf.fit.travie_api.entity.AppUser;

@Mapper
public interface UserMapper {
    UserProfileDTO toUserProfileDTO(AppUser appUser);
}
