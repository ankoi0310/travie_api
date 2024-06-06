package vn.edu.hcmuaf.fit.travie_api.dto.room;

import jakarta.validation.constraints.*;
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

    private String description;

    @NotNull
//    @Pattern(regexp = "^[1-9]\\d{3,}$")
    @Positive
    private int price;

    @NotNull
    private HotelDTO hotel;

    @NotEmpty
    private List<@NotNull AmenityDTO> amenities;
}
