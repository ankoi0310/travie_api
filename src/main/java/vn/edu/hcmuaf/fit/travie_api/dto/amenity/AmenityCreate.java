package vn.edu.hcmuaf.fit.travie_api.dto.amenity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AmenityCreate {
    private String name;
    private String description;
}
