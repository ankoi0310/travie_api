package vn.edu.hcmuaf.fit.travie_api.dto.banner;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.hc.client5.http.entity.mime.MultipartPart;

@Data
@EqualsAndHashCode(callSuper = true)
public class BannerUpdate extends BannerDTO {
    private MultipartPart newImage;
}
