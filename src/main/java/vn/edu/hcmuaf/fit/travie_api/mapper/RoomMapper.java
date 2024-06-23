package vn.edu.hcmuaf.fit.travie_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.travie_api.dto.room.RoomDTO;
import vn.edu.hcmuaf.fit.travie_api.entity.Room;

import java.util.List;

@Mapper(
        uses = {AmenityMapper.class}
)
public interface RoomMapper {
    @Named("toRoomDTO")
    @Mapping(target = "amenities", qualifiedByName = "toAmenityDTOs")
    @Mapping(target = "images", expression = "java(room.getImages())")
    @Mapping(target = "hotel", ignore = true)
    RoomDTO toRoomDTO(Room room);

    @Named("toRoomDTOWithHotel")
    @Mapping(target = "amenities", qualifiedByName = "toAmenityDTOs")
    @Mapping(target = "images", expression = "java(room.getImages())")
    @Mapping(target = "hotel.rooms", ignore = true)
    RoomDTO toRoomDTOWithHotel(Room room);

    @Named("toRoomDTOs")
    @IterableMapping(qualifiedByName = "toRoomDTO")
    List<RoomDTO> toRoomDTOs(List<Room> rooms);

    @Named("toRoomDTOsWithHotel")
    @IterableMapping(qualifiedByName = "toRoomDTOWithHotel")
    List<RoomDTO> toRoomDTOsWithHotel(List<Room> rooms);
}
