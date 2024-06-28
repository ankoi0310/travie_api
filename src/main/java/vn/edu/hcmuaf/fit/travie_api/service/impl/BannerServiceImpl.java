package vn.edu.hcmuaf.fit.travie_api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.fit.travie_api.core.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.core.exception.NotFoundException;
import vn.edu.hcmuaf.fit.travie_api.core.infrastructure.firebase.FirebaseService;
import vn.edu.hcmuaf.fit.travie_api.dto.banner.*;
import vn.edu.hcmuaf.fit.travie_api.entity.Banner;
import vn.edu.hcmuaf.fit.travie_api.mapper.BannerMapper;
import vn.edu.hcmuaf.fit.travie_api.repository.BannerRepository;
import vn.edu.hcmuaf.fit.travie_api.service.BannerService;

@Log4j2
@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {
    private final BannerRepository bannerRepository;
    private final BannerMapper bannerMapper;
    private final FirebaseService firebaseService;

    @Override
    public Page<BannerDTO> getBanners(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Banner> banners = bannerRepository.findAll(pageable);
        return banners.map(bannerMapper::toDTO);
    }

    @Override
    public BannerDTO getBannerById(long id) throws BaseException {
        Banner banner = bannerRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tìm thấy banner"));

        BannerDTO bannerDTO = bannerMapper.toDTO(banner);

        // Get image from firebase
        // bannerDTO.setImage(firebaseService.getImage(banner.getImage()));

        return bannerDTO;
    }

    @Override
    public BannerDTO createBanner(BannerCreate bannerCreate) throws BaseException {
        bannerRepository.findByTitle(bannerCreate.getTitle())
                        .orElseThrow(() -> new BaseException("Banner đã tồn tại"));
        Banner banner = Banner.builder()
                              .title(bannerCreate.getTitle())
                              .description(bannerCreate.getDescription())
                              .url(bannerCreate.getUrl())
//                              .image(firebaseService.uploadImage(bannerCreate.getImage()))
                              .build();

        // Save image to firebase
        // banner.setImage(firebaseService.uploadImage(bannerCreate.getImage()));

        bannerRepository.save(banner);
        return bannerMapper.toDTO(banner);
    }

    @Override
    public BannerDTO updateBanner(BannerUpdate bannerUpdate) throws BaseException {
        bannerRepository.findById(bannerUpdate.getId())
                        .orElseThrow(() -> new NotFoundException("Không tìm thấy banner"));

        Banner updateBanner = bannerMapper.toEntity(bannerUpdate);

        // Save new image to firebase if it's not null and different from the old one, then update the image link
        // after that, save the banner to the database with the new image link and remove the old image from firebase
        // if (bannerUpdate.getImage() != null) {
        //     banner.setImage(firebaseService.uploadImage(bannerUpdate.getImage()));
        // }

        bannerRepository.save(updateBanner);
        return bannerMapper.toDTO(updateBanner);
    }

    @Override
    public void deleteBanner(long id) throws BaseException {
        Banner banner = bannerRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tìm thấy banner"));
        bannerRepository.delete(banner);

        // Remove image from firebase
        // firebaseService.deleteImage(banner.getImage());
    }
}
