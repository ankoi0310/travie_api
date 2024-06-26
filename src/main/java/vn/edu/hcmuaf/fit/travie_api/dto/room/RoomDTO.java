package vn.edu.hcmuaf.fit.travie_api.dto.room;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.travie_api.core.dto.BaseDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.amenity.AmenityDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.hotel.HotelDTO;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RoomDTO extends BaseDTO {
    private String name;
    private int numOfRooms;
    private int firstHoursOrigin;
    private int minNumHours;
    private int maxNumHours;
    private int oneDayOrigin;
    private int overnightOrigin;
    private int additionalHours;
    private int additionalOrigin;
    private boolean hasDiscount;
    private boolean applyFlashSale;
    private int priceFlashSale;
    private boolean hasExtraFee;
    private boolean available;
    private boolean availableTonight;
    private boolean availableTomorrow;
    private boolean soldOut;
    private List<AmenityDTO> amenities;
    private List<String> images;
    private int status;

    @JsonIgnoreProperties({"rooms"})
    private HotelDTO hotel;
}
