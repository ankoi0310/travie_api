package vn.edu.hcmuaf.fit.travie_api.dto.banner;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BannerCreate {
    private String title;
    private String description;
    private MultipartFile image;
    private String url;
}
