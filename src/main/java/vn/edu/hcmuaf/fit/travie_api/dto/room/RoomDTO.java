package vn.edu.hcmuaf.fit.travie_api.dto.room;

import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.travie_api.core.dto.BaseDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.amenity.AmenityDTO;

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
    private List<AmenityDTO> amenities;
    private List<RoomImageDTO> roomImages;
}
