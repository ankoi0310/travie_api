package vn.edu.hcmuaf.fit.travie_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.travie_api.core.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.dto.amenity.*;
import vn.edu.hcmuaf.fit.travie_api.service.AmenityService;

import java.util.List;

@RestController
@RequestMapping("/amenity")
@RequiredArgsConstructor
public class AmenityController {
    private final AmenityService amenityService;

    @GetMapping
    public ResponseEntity<List<AmenityDTO>> getAmenities() {
        List<AmenityDTO> amenities = amenityService.getAllAmenities();
        return ResponseEntity.ok(amenities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AmenityDTO> getAmenityById(@PathVariable long id) throws BaseException {
        AmenityDTO amenity = amenityService.getAmenityById(id);
        return ResponseEntity.ok(amenity);
    }

    @PostMapping
    public ResponseEntity<AmenityDTO> createAmenity(@ModelAttribute AmenityCreate amenityCreate) throws BaseException {
        AmenityDTO amenity = amenityService.createAmenity(amenityCreate);
        return ResponseEntity.ok(amenity);
    }

    @PutMapping
    public ResponseEntity<AmenityDTO> updateAmenity(@ModelAttribute AmenityUpdate amenityUpdate) throws BaseException {
        AmenityDTO amenity = amenityService.updateAmenity(amenityUpdate);
        return ResponseEntity.ok(amenity);
    }
}
