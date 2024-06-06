package vn.edu.hcmuaf.fit.travie_api.dto.booking;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.BookingStatus;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.PaymentStatus;
import vn.edu.hcmuaf.fit.travie_api.dto.bookingtype.BookingTypeDTO;
import vn.edu.hcmuaf.fit.travie_api.entity.Room;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    @JsonIgnore
    @JsonIgnoreProperties({"amenities.rooms"})
    private Room room;
    private BookingTypeDTO type;
    private Integer duration;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private LocalDateTime checkIn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private LocalDateTime checkOut;

    private String phone;
    private String guestName;
    private int price;
    private int discount;
    private int total;
    private String note;
    private BookingStatus bookingStatus;
    private PaymentStatus paymentStatus;
}
