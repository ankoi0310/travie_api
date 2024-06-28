package vn.edu.hcmuaf.fit.travie_api.dto.hotel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.travie_api.dto.address.AddressCreate;
import vn.edu.hcmuaf.fit.travie_api.dto.amenity.AmenityDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.room.RoomCreate;

import java.util.List;
import java.util.stream.Collectors;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class HotelCreate {
    @NotBlank
    private String name;

    private String introduction;

    @NotNull
    private AddressCreate address;

    private double rating;

    private List<RoomCreate> rooms;

    public List<Long> getAllAmenityIds() {
        return rooms.stream()
                    .flatMap(room -> room.getAmenities().stream())
                    .map(AmenityDTO::getId)
                    .collect(Collectors.toList());
    }
}
