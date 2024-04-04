package vn.edu.hcmuaf.fit.travie_api.service.facility;

import vn.edu.hcmuaf.fit.travie_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.dto.facility.*;

import java.util.List;

public interface FacilityService {
    List<FacilityDTO> getAllFacilities();

    FacilityDTO getFacilityById(Long id) throws BaseException;

    FacilityDTO createFacility(FacilityCreate facilityCreate) throws BaseException;

    FacilityDTO updateFacility(FacilityUpdate facilityUpdate) throws BaseException;

    void updateFacilityStatus(Long id, boolean enable) throws BaseException;
}
