package vn.edu.hcmuaf.fit.travie_api.dto.hotel;

import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.travie_api.core.dto.BaseDTO;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.HotelStatus;
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
    private AddressDTO address;
    private List<BookingTypeDTO> bookingTypes;
    private List<RoomDTO> rooms;
    private List<String> images;
    private List<AmenityDTO> amenities;
    private HotelStatus status;
    private double rating;
    private List<ReviewDTO> reviews;
}
