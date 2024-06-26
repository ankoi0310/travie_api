package vn.edu.hcmuaf.fit.travie_api.dto.hotel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.travie_api.core.dto.BaseDTO;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.hotel.HotelStatus;
import vn.edu.hcmuaf.fit.travie_api.dto.address.AddressDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.amenity.AmenityDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.bookingtype.BookingTypeDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.review.ReviewDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.room.RoomDTO;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class HotelDTO extends BaseDTO {
    private String name;
    private String introduction;
    private int firstHours;
    private int checkIn;
    private int checkOut;
    private boolean daily;
    private int startHourly;
    private int endHourly;
    private boolean hourly;
    private int startOvernight;
    private int endOvernight;
    private boolean overnight;
    private int cancelBeforeHours;
    private AddressDTO address;
    private List<BookingTypeDTO> bookingTypes;

    @JsonIgnoreProperties({"hotel"})
    private List<RoomDTO> rooms;
    private List<String> images;
    private List<AmenityDTO> amenities;
    private HotelStatus status;
    private double averageMark;
    private double averageMarkClean;
    private double averageMarkAmenity;
    private double averageMarkService;
    private List<ReviewDTO> reviews;
}
