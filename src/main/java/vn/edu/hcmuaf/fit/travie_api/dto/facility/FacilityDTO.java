package vn.edu.hcmuaf.fit.travie_api.dto.facility;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.travie_api.core.dto.BaseDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.room.RoomDTO;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FacilityDTO extends BaseDTO {
    private String name;

    @JsonIgnore
    @JsonProperty("rooms")
    private List<RoomDTO> rooms;

    private boolean deleted;
}
