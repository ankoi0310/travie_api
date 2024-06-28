package vn.edu.hcmuaf.fit.travie_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.travie_api.core.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.core.handler.HttpResponse;
import vn.edu.hcmuaf.fit.travie_api.dto.banner.*;
import vn.edu.hcmuaf.fit.travie_api.service.BannerService;

@RestController
@RequestMapping("/banner")
@RequiredArgsConstructor
public class BannerController {
    private final BannerService bannerService;

    @GetMapping
    public ResponseEntity<HttpResponse> getBanners(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<BannerDTO> banners = bannerService.getBanners(page, size);
        return ResponseEntity.ok(HttpResponse.success(banners, "Lấy danh sách banner thành công"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> getBannerById(@PathVariable long id) throws BaseException {
        BannerDTO banner = bannerService.getBannerById(id);
        return ResponseEntity.ok(HttpResponse.success(banner, "Lấy thông tin banner thành công"));
    }

    @PostMapping
    public ResponseEntity<HttpResponse> createBanner(@RequestBody BannerCreate bannerCreate) throws BaseException {
        BannerDTO banner = bannerService.createBanner(bannerCreate);
        return ResponseEntity.ok(HttpResponse.success(banner, "Tạo banner thành công"));
    }

    @PutMapping
    public ResponseEntity<HttpResponse> updateBanner(@RequestBody BannerUpdate bannerUpdate) throws BaseException {
        BannerDTO banner = bannerService.updateBanner(bannerUpdate);
        return ResponseEntity.ok(HttpResponse.success(banner, "Cập nhật banner thành công"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpResponse> deleteBanner(@PathVariable long id) throws BaseException {
        bannerService.deleteBanner(id);
        return ResponseEntity.ok(HttpResponse.success(null, "Xóa banner thành công"));
    }
}
