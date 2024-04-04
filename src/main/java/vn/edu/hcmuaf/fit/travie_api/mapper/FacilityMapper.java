package vn.edu.hcmuaf.fit.travie_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.edu.hcmuaf.fit.travie_api.dto.facility.FacilityDTO;
import vn.edu.hcmuaf.fit.travie_api.entity.Facility;

import java.util.List;

@Mapper
public interface FacilityMapper {
    FacilityDTO toDTO(Facility facility);

    Facility toEntity(FacilityDTO facilityDTO);

    List<FacilityDTO> toDTOs(List<Facility> facilities);

    List<Facility> toEntities(List<FacilityDTO> facilityDTOs);
}
