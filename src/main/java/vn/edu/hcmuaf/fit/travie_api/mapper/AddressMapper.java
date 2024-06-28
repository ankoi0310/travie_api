package vn.edu.hcmuaf.fit.travie_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.travie_api.dto.address.AddressDTO;
import vn.edu.hcmuaf.fit.travie_api.entity.Address;

import java.util.List;

@Mapper
public interface AddressMapper {
    @Named("toDTO")
    AddressDTO toDTO(Address address);

    @Named("toEntity")
    Address toEntity(AddressDTO addressDTO);

    @IterableMapping(qualifiedByName = "toDTO")
    List<AddressDTO> toDTOs(List<Address> addresses);

    @IterableMapping(qualifiedByName = "toEntity")
    List<Address> toEntities(List<AddressDTO> addressDTOs);
}
