package vn.edu.hcmuaf.fit.travie_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.travie_api.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.dto.facility.*;
import vn.edu.hcmuaf.fit.travie_api.service.facility.FacilityService;

import java.util.List;

@RestController
@RequestMapping("/facility")
@RequiredArgsConstructor
public class FacilityController {
    private final FacilityService facilityService;

    @GetMapping
    public ResponseEntity<HttpResponse> getFacilities() {
        List<FacilityDTO> facilityDTOS = facilityService.getAllFacilities();
        return ResponseEntity.ok(HttpResponse.success(facilityDTOS, "Lấy danh sách tiện ích thành công!"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> getFacilityById(@PathVariable long id) throws BaseException {
        FacilityDTO facilityDTO = facilityService.getFacilityById(id);
        return ResponseEntity.ok(HttpResponse.success(facilityDTO, "Lấy tiện ích thành công"));
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<HttpResponse> createFacility(@ModelAttribute FacilityCreate facilityCreate) throws BaseException {
        FacilityDTO facilityDTO = facilityService.createFacility(facilityCreate);
        return ResponseEntity.ok(HttpResponse.success(facilityDTO, "Tạo tiện ích thành công!"));
    }

    @PutMapping
    public ResponseEntity<HttpResponse> updateFacility(@ModelAttribute FacilityUpdate facilityUpdate) throws BaseException {
        FacilityDTO facilityDTO = facilityService.updateFacility(facilityUpdate);
        return ResponseEntity.ok(HttpResponse.success(facilityDTO, "Cập nhật tiện ích thành công"));
    }

    @PutMapping
    public ResponseEntity<HttpResponse> updateFacilityStatus(@RequestParam long id, @RequestParam boolean enable) throws BaseException {
        facilityService.updateFacilityStatus(id, enable);
        return ResponseEntity.ok(HttpResponse.success(null, "Cập nhật trạng thái tiện ích thành công"));
    }
}
