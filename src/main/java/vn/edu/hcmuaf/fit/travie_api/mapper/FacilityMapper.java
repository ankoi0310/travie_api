package vn.edu.hcmuaf.fit.travie_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.travie_api.dto.facility.FacilityDTO;
import vn.edu.hcmuaf.fit.travie_api.entity.Facility;

import java.util.List;

@Mapper
public interface FacilityMapper {
    @Named("toDTOWithoutRooms")
    @Mapping(target = "rooms", ignore = true)
    FacilityDTO toDTOWithoutRooms(Facility facility);

    @Named("toEntity")
    Facility toEntity(FacilityDTO facilityDTO);

    @IterableMapping(qualifiedByName = "toEntity")
    List<Facility> toEntities(List<FacilityDTO> facilityDTOs);
}
