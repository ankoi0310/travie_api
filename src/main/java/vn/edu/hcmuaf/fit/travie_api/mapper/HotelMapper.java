package vn.edu.hcmuaf.fit.travie_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.travie_api.dto.hotel.HotelDTO;
import vn.edu.hcmuaf.fit.travie_api.entity.Hotel;

import java.util.List;

@Mapper(
        uses = {RoomMapper.class, AmenityMapper.class, ReviewMapper.class}
)
public interface HotelMapper {
    @Named("toDTO")
    @Mapping(target = "rooms", qualifiedByName = "toRoomDTOs")
    @Mapping(target = "reviews", qualifiedByName = "toReviewDTOs")
    HotelDTO toDTO(Hotel hotel);

    @Named("toEntity")
    Hotel toEntity(HotelDTO hotelDTO);

    @IterableMapping(qualifiedByName = "toDTO")
    List<HotelDTO> toDTOs(List<Hotel> hotels);

    @IterableMapping(qualifiedByName = "toEntity")
    List<Hotel> toEntities(List<HotelDTO> hotelDTOs);
}
