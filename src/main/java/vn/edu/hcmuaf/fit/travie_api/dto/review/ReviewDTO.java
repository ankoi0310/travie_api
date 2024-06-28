package vn.edu.hcmuaf.fit.travie_api.dto.review;

import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.travie_api.core.dto.BaseDTO;
import vn.edu.hcmuaf.fit.travie_api.entity.Hotel;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReviewDTO extends BaseDTO {
    private UserReviewDTO user;
    private int rating;
    private String comment;
    private Hotel hotel;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserReviewDTO {
        private String username;
        private String avatar;
    }
}
