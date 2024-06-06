package vn.edu.hcmuaf.fit.travie_api.dto.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import vn.edu.hcmuaf.fit.travie_api.dto.room.RoomDTO;
import vn.edu.hcmuaf.fit.travie_api.entity.BookingType;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
    private RoomDTO room;
    private BookingType type;
    private int duration;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private LocalDateTime checkIn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private LocalDateTime checkOut;

    private String phone;
    private String guestName;
}
