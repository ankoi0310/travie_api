package vn.edu.hcmuaf.fit.travie_api.dto.room;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.travie_api.dto.amenity.AmenityDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.hotel.HotelDTO;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RoomCreate {
    @NotNull
    private String name;

    private int numOfRooms;
    private int firstHoursOrigin;
    private int minNumHours;
    private int maxNumHours;
    private int oneDayOrigin;
    private int overNightOrigin;
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
    private int status;

    @NotNull
    private HotelDTO hotel;

    @NotEmpty
    private List<@NotNull AmenityDTO> amenities;
}
