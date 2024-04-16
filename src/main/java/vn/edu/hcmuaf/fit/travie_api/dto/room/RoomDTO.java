package vn.edu.hcmuaf.fit.travie_api.dto.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.travie_api.core.dto.BaseDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.facility.FacilityDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.hotel.HotelDTO;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RoomDTO extends BaseDTO {
    private String name;
    private String description;
    private int price;
    private List<FacilityDTO> facilities;

    @JsonIgnore
    @JsonIgnoreProperties({"rooms"})
    private HotelDTO hotel;
}
