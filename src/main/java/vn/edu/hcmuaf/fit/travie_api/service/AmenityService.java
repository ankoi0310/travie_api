package vn.edu.hcmuaf.fit.travie_api.service;

import vn.edu.hcmuaf.fit.travie_api.core.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.dto.amenity.*;

import java.util.List;

public interface AmenityService {
    List<AmenityDTO> getAllAmenities();

    AmenityDTO getAmenityById(long id) throws BaseException;

    AmenityDTO createAmenity(AmenityCreate amenityCreate) throws BaseException;

    AmenityDTO updateAmenity(AmenityUpdate amenityUpdate) throws BaseException;

    void updateAmenityStatus(long id, boolean enable) throws BaseException;
}
