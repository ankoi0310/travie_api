package vn.edu.hcmuaf.fit.travie_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.travie_api.dto.invoice.InvoiceDTO;
import vn.edu.hcmuaf.fit.travie_api.entity.Invoice;

import java.util.List;

@Mapper(
        uses = {RoomMapper.class, BookingTypeMapper.class}
)
public interface InvoiceMapper {
    @Named("toInvoiceDTO")
    @Mapping(target = "room", qualifiedByName = "toRoomDTOWithHotel")
    @Mapping(target = "bookingType", qualifiedByName = "toBookingTypeDTO")
    InvoiceDTO toInvoiceDTO(Invoice invoice);

    @Named("toInvoiceDTOs")
    @IterableMapping(qualifiedByName = "toInvoiceDTO")
    List<InvoiceDTO> toInvoiceDTOs(List<Invoice> invoices);
}
