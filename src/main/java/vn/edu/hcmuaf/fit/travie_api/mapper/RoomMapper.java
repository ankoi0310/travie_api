package vn.edu.hcmuaf.fit.travie_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.travie_api.dto.room.RoomDTO;
import vn.edu.hcmuaf.fit.travie_api.entity.Room;

import java.util.List;

@Mapper(
        uses = {AmenityMapper.class, RoomImageMapper.class}
)
public interface RoomMapper {
    @Named("toRoomDTO")
    @Mapping(target = "amenities", qualifiedByName = "toAmenityDTOs")
    @Mapping(target = "roomImages", qualifiedByName = "toRoomImageDTOs")
    RoomDTO toRoomDTO(Room room);

    @Named("toRoomDTOs")
    @IterableMapping(qualifiedByName = "toRoomDTO")
    List<RoomDTO> toRoomDTOs(List<Room> rooms);
}
