package vn.edu.hcmuaf.fit.travie_api.dto.facility;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacilityCreate {
    private String name;
}
