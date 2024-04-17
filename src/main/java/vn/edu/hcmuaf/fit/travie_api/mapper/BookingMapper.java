package vn.edu.hcmuaf.fit.travie_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.travie_api.dto.booking.BookingDTO;
import vn.edu.hcmuaf.fit.travie_api.entity.Booking;

import java.util.List;

@Mapper
public interface BookingMapper {
    @Named("toDTO")
    BookingDTO toDTO(Booking booking);

    @Named("toEntity")
    Booking toEntity(BookingDTO bookingDTO);

    @IterableMapping(qualifiedByName = "toDTO")
    List<BookingDTO> toDTOs(List<Booking> bookings);

    @IterableMapping(qualifiedByName = "toEntity")
    List<Booking> toEntities(List<BookingDTO> bookingDTOs);
}
