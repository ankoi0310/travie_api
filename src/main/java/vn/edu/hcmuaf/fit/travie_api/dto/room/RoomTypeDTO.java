package vn.edu.hcmuaf.fit.travie_api.dto.room;

import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.travie_api.core.dto.BaseDTO;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RoomTypeDTO extends BaseDTO {
    private String name;
    private String description;
}
