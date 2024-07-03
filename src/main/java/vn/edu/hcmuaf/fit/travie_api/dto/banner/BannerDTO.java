package vn.edu.hcmuaf.fit.travie_api.dto.banner;

import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.travie_api.core.dto.BaseDTO;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BannerDTO extends BaseDTO {
    private String title;
    private String description;
    private String image;
    private String url;
}
