package vn.edu.hcmuaf.fit.travie_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.travie_api.dto.banner.BannerDTO;
import vn.edu.hcmuaf.fit.travie_api.entity.Banner;

import java.util.List;

@Mapper
public interface BannerMapper {
    @Named("toDTO")
    BannerDTO toDTO(Banner banner);

    @Named("toEntity")
    Banner toEntity(BannerDTO bannerDTO);

    @IterableMapping(qualifiedByName = "toDTO")
    List<BannerDTO> toDTOs(List<Banner> banners);

    @IterableMapping(qualifiedByName = "toEntity")
    List<Banner> toEntities(List<BannerDTO> bannerDTOs);
}
