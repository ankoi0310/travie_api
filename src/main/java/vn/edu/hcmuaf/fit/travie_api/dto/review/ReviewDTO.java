package vn.edu.hcmuaf.fit.travie_api.dto.review;

import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.travie_api.core.dto.BaseDTO;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReviewDTO extends BaseDTO {
    private AppUserReviewDTO user;
    private int rating;
    private String comment;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AppUserReviewDTO {
        private String username;
        private String avatar;
    }
}
