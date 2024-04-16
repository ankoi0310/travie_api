package vn.edu.hcmuaf.fit.travie_api.service.facility;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.fit.travie_api.core.handler.exception.*;
import vn.edu.hcmuaf.fit.travie_api.dto.facility.*;
import vn.edu.hcmuaf.fit.travie_api.entity.Facility;
import vn.edu.hcmuaf.fit.travie_api.mapper.RoomFacilityMapper;
import vn.edu.hcmuaf.fit.travie_api.repository.facility.FacilityRepository;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class FacilityServiceImpl implements FacilityService {
    private final FacilityRepository facilityRepository;
    private final RoomFacilityMapper mapper;

    @Override
    public List<FacilityDTO> getAllFacilities() {
        return mapper.toFacilityDTOs(facilityRepository.findAll());
    }

    @Override
    public FacilityDTO getFacilityById(Long id) throws BaseException {
        return facilityRepository.findById(id)
                                 .map(mapper::toFacilityDTO)
                                 .orElseThrow(() -> new NotFoundException("Không tìm thấy tiện ích"));
    }

    @Override
    public FacilityDTO createFacility(FacilityCreate facilityCreate) throws BaseException {
        try {
            facilityRepository.findByName(facilityCreate.getName())
                              .orElseThrow(() -> new BadRequestException("Tiện ích đã tồn tại"));

            Facility newFacility = Facility.builder()
                                           .name(facilityCreate.getName())
                                           .build();

            facilityRepository.save(newFacility);

            return mapper.toFacilityDTO(newFacility);
        } catch (BadRequestException e) {
            log.error(e);
            throw e;
        } catch (Exception e) {
            log.error(e);
            throw new ServiceBusinessException("Xảy ra lỗi khi tạo tiện ích.");
        }
    }

    @Override
    public FacilityDTO updateFacility(FacilityUpdate facilityUpdate) throws BaseException {
        try {
            Facility facility = facilityRepository.findById(facilityUpdate.getId())
                                                  .orElseThrow(() -> new NotFoundException("Không tìm thấy tiện ích"));

            Optional<Facility> facilityByName = facilityRepository.findByName(facilityUpdate.getName());
            if (facilityByName.isPresent() && !facilityByName.get().getId().equals(facilityUpdate.getId())) {
                throw new BadRequestException("Tiện ích đã tồn tại");
            }

            facility.setName(facilityUpdate.getName());
            facilityRepository.save(facility);

            return mapper.toFacilityDTO(facility);
        } catch (BadRequestException e) {
            log.error(e);
            throw e;
        } catch (Exception e) {
            log.error(e);
            throw new ServiceBusinessException("Xảy ra lỗi khi cập nhật tiện ích.");
        }
    }

    @Override
    public void updateFacilityStatus(Long id, boolean delete) throws BaseException {
        try {
            Facility facility = facilityRepository.findById(id)
                                                  .orElseThrow(() -> new NotFoundException("Không tìm thấy tiện ích"));

            facility.setDeleted(delete);
            facilityRepository.save(facility);
        } catch (Exception e) {
            log.error(e);
            throw new ServiceBusinessException("Xảy ra lỗi khi cập nhật trạng thái tiện ích.");
        }
    }
}
