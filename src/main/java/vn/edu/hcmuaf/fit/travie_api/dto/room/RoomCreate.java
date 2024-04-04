package vn.edu.hcmuaf.fit.travie_api.dto.room;

import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.travie_api.dto.facility.FacilityDTO;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RoomCreate {
    private String name;
    private String description;
    private int price;
    private List<FacilityDTO> facilities;
}
