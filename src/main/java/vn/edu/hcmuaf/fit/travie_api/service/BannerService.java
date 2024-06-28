package vn.edu.hcmuaf.fit.travie_api.service;

import org.springframework.data.domain.Page;
import vn.edu.hcmuaf.fit.travie_api.core.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.dto.banner.*;

public interface BannerService {
    Page<BannerDTO> getBanners(int page, int size);

    BannerDTO getBannerById(long id) throws BaseException;

    BannerDTO createBanner(BannerCreate bannerCreate) throws BaseException;

    BannerDTO updateBanner(BannerUpdate bannerUpdate) throws BaseException;

    void deleteBanner(long id) throws BaseException;
}
