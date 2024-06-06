package vn.edu.hcmuaf.fit.travie_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.travie_api.dto.room.RoomTypeDTO;
import vn.edu.hcmuaf.fit.travie_api.entity.RoomType;

import java.util.List;

@Mapper
public interface RoomTypeMapper {
    @Named("toRoomTypeDTO")
    RoomTypeDTO toRoomTypeDTO(RoomType roomType);

    @Named("toRoomTypeDTOs")
    @IterableMapping(qualifiedByName = "toRoomTypeDTO")
    List<RoomTypeDTO> toRoomTypeDTOs(List<RoomType> roomTypes);
}
