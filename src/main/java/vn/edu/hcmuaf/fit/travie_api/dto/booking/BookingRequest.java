package vn.edu.hcmuaf.fit.travie_api.dto.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.invoice.PaymentMethod;
import vn.edu.hcmuaf.fit.travie_api.dto.bookingtype.BookingTypeDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.room.RoomDTO;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
    private RoomDTO room;
    private BookingTypeDTO bookingType;
    private String guestName;
    private String guestPhone;
    private int totalPrice;
    private int finalPrice;
    private PaymentMethod paymentMethod;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private LocalDateTime checkIn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private LocalDateTime checkOut;
}
