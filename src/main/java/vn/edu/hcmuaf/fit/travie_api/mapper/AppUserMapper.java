package vn.edu.hcmuaf.fit.travie_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.travie_api.dto.user.AppUserDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.user.UserProfileDTO;
import vn.edu.hcmuaf.fit.travie_api.entity.AppUser;

@Mapper(uses = {UserInfoMapper.class})
public interface AppUserMapper {
    @Mappings({
            @Mapping(target = "role", source = "appRole.name"),
            @Mapping(target = "userInfoDTO", source = "userInfo")
    })
    AppUserDTO toDTO(AppUser appUser);

    @Mappings({
            @Mapping(target = "fullName", source = "userInfo.fullName"),
            @Mapping(target = "gender", source = "userInfo.gender.value"),
            @Mapping(target = "birthday", source = "userInfo.birthday"),
            @Mapping(target = "avatar", source = "userInfo.avatar"),
    })
    UserProfileDTO toProfileDTO(AppUser appUser);
}
