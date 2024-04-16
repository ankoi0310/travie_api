package vn.edu.hcmuaf.fit.travie_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.travie_api.dto.facility.FacilityDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.room.RoomDTO;
import vn.edu.hcmuaf.fit.travie_api.entity.Facility;
import vn.edu.hcmuaf.fit.travie_api.entity.Room;

import java.util.List;

@Mapper(
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {RoomMapper.class, FacilityMapper.class}
)
public interface RoomFacilityMapper {
    @Named("toFacilityDTO")
    @Mapping(target = "rooms", source = "rooms", qualifiedByName = "toDTOWithoutFacilities")
    FacilityDTO toFacilityDTO(Facility facility);

    @IterableMapping(qualifiedByName = "toFacilityDTO")
    List<FacilityDTO> toFacilityDTOs(List<Facility> facilities);

    @Named("toRoomDTO")
    @Mapping(target = "facilities", source = "facilities", qualifiedByName = "toDTOWithoutRooms")
    RoomDTO toRoomDTO(Room room);

    @IterableMapping(qualifiedByName = "toRoomDTO")
    List<RoomDTO> toRoomDTOs(List<Room> rooms);
}
