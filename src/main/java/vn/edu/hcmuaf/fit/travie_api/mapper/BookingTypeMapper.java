package vn.edu.hcmuaf.fit.travie_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.travie_api.dto.bookingtype.BookingTypeDTO;
import vn.edu.hcmuaf.fit.travie_api.entity.BookingType;

import java.util.List;

@Mapper
public interface BookingTypeMapper {
    @Named("toBookingTypeDTO")
    BookingTypeDTO toBookingTypeDTO(BookingType bookingType);

    @Named("toBookingType")
    BookingType toBookingType(BookingTypeDTO bookingTypeDTO);

    @Named("toBookingTypeDTOs")
    @IterableMapping(qualifiedByName = "toBookingTypeDTO")
    List<BookingTypeDTO> toBookingTypeDTOs(List<BookingType> bookingTypes);

    @Named("toBookingTypes")
    @IterableMapping(qualifiedByName = "toBookingType")
    List<BookingType> toBookingTypes(List<BookingTypeDTO> bookingTypeDTOs);
}
