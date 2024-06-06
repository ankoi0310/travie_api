package vn.edu.hcmuaf.fit.travie_api.dto.amenity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.travie_api.core.dto.BaseDTO;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AmenityDTO extends BaseDTO {
    private String name;
    private boolean deleted;
}
