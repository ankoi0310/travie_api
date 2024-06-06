package vn.edu.hcmuaf.fit.travie_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.travie_api.dto.booking.BookingDTO;
import vn.edu.hcmuaf.fit.travie_api.entity.Invoice;

import java.util.List;

@Mapper
public interface BookingMapper {
    @Named("toDTO")
    BookingDTO toDTO(Invoice invoice);

    @Named("toEntity")
    Invoice toEntity(BookingDTO bookingDTO);

    @IterableMapping(qualifiedByName = "toDTO")
    List<BookingDTO> toDTOs(List<Invoice> invoices);

    @IterableMapping(qualifiedByName = "toEntity")
    List<Invoice> toEntities(List<BookingDTO> bookingDTOs);
}
