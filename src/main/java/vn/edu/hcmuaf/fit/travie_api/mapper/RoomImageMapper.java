package vn.edu.hcmuaf.fit.travie_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.travie_api.dto.room.RoomImageDTO;
import vn.edu.hcmuaf.fit.travie_api.entity.RoomImage;

import java.util.List;

@Mapper(
        uses = {RoomTypeMapper.class}
)
public interface RoomImageMapper {
    @Named("toRoomImageDTO")
    @Mapping(target = "roomType", qualifiedByName = "toRoomTypeDTO")
    RoomImageDTO toRoomImageDTO(RoomImage roomImage);

    @Named("toRoomImageDTOs")
    @IterableMapping(qualifiedByName = "toRoomImageDTO")
    List<RoomImageDTO> toRoomImageDTOs(List<RoomImage> roomImages);
}
