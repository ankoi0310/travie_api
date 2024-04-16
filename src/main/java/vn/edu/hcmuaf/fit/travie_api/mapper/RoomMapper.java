package vn.edu.hcmuaf.fit.travie_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.travie_api.dto.room.RoomDTO;
import vn.edu.hcmuaf.fit.travie_api.entity.Room;

import java.util.List;

@Mapper
public interface RoomMapper {
    @Named("toDTOWithoutFacilities")
    @Mapping(target = "facilities", ignore = true)
    RoomDTO toDTOWithoutFacilities(Room room);

    @Named("toEntity")
    Room toEntity(RoomDTO roomDTO);

    @IterableMapping(qualifiedByName = "toEntity")
    List<Room> toEntities(List<RoomDTO> roomDTOs);
}
