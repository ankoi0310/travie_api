package vn.edu.hcmuaf.fit.travie_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.travie_api.dto.room.RoomDTO;
import vn.edu.hcmuaf.fit.travie_api.entity.Room;

import java.util.List;

@Mapper
public interface RoomMapper {
    @Named("toDTO")
    RoomDTO toDTO(Room room);

    @Named("toEntity")
    Room toEntity(RoomDTO roomDTO);

    @IterableMapping(qualifiedByName = "toDTO")
    List<RoomDTO> toDTOs(List<Room> rooms);

    @IterableMapping(qualifiedByName = "toEntity")
    List<Room> toEntities(List<RoomDTO> roomDTOs);
}
