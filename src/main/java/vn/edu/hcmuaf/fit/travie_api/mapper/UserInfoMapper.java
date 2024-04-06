package vn.edu.hcmuaf.fit.travie_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.edu.hcmuaf.fit.travie_api.dto.user.UserInfoDTO;
import vn.edu.hcmuaf.fit.travie_api.entity.UserInfo;

@Mapper
public interface UserInfoMapper {
    @Mapping(target = "gender", source = "gender.value")
    UserInfoDTO toDTO(UserInfo userInfo);
}
