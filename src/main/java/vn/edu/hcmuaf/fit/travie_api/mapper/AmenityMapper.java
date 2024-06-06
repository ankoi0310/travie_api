package vn.edu.hcmuaf.fit.travie_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.travie_api.dto.amenity.AmenityDTO;
import vn.edu.hcmuaf.fit.travie_api.entity.Amenity;

import java.util.List;

@Mapper
public interface AmenityMapper {
    @Named("toAmenityDTO")
    AmenityDTO toAmenityDTO(Amenity amenity);

    @Named("toAmenityEntity")
    Amenity toAmenityEntity(AmenityDTO amenityDTO);

    @Named("toAmenityDTOs")
    @IterableMapping(qualifiedByName = "toAmenityDTO")
    List<AmenityDTO> toAmenityDTOs(List<Amenity> amenities);

    @IterableMapping(qualifiedByName = "toAmenityEntity")
    List<Amenity> toAmenityEntities(List<AmenityDTO> amenityDTOs);
}
