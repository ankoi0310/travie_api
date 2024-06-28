package vn.edu.hcmuaf.fit.travie_api.dto.invoice;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.travie_api.core.dto.BaseDTO;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.invoice.*;
import vn.edu.hcmuaf.fit.travie_api.dto.bookingtype.BookingTypeDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.room.RoomDTO;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InvoiceDTO extends BaseDTO {
    private int code;
    private RoomDTO room;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private LocalDateTime checkIn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private LocalDateTime checkOut;
    private BookingTypeDTO bookingType;
    private String guestPhone;
    private String guestName;
    private int totalPrice;
    private int finalPrice;
    private PaymentMethod paymentMethod;
    private BookingStatus bookingStatus;
    private PaymentStatus paymentStatus;
}
