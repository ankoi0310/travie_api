package vn.edu.hcmuaf.fit.travie_api.dto.hotel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.travie_api.core.dto.BaseDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.address.AddressDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.room.RoomDTO;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class HotelDTO extends BaseDTO {
    private String name;
    private String description;
    private AddressDTO address;
    private Double rating;

    @JsonIgnore
    @JsonIgnoreProperties({"hotel"})
    private List<RoomDTO> rooms;
}
