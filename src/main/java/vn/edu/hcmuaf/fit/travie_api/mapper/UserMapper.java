package vn.edu.hcmuaf.fit.travie_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.edu.hcmuaf.fit.travie_api.dto.user.UserProfileDTO;
import vn.edu.hcmuaf.fit.travie_api.entity.AppUser;

@Mapper
public interface UserMapper {
    @Mapping(target = "fullName", source = "userInfo.fullName")
    @Mapping(target = "avatar", source = "userInfo.avatar")
    @Mapping(target = "birthday", source = "userInfo.birthday")
    @Mapping(target = "gender", source = "userInfo.gender")
    UserProfileDTO toUserProfileDTO(AppUser appUser);
}
