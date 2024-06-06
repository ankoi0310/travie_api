package vn.edu.hcmuaf.fit.travie_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.travie_api.dto.review.ReviewDTO;
import vn.edu.hcmuaf.fit.travie_api.entity.Review;

import java.util.List;

@Mapper
public interface ReviewMapper {
    @Named("toReviewDTO")
    @Mapping(target = "user.username", source = "user.username")
    @Mapping(target = "user.avatar", source = "user.userInfo.avatar")
    ReviewDTO toReviewDTO(Review review);

    @Named("toReviewEntity")
    @Mapping(target = "user.username", source = "user.username")
    @Mapping(target = "user.userInfo.avatar", source = "user.avatar")
    Review toReviewEntity(ReviewDTO reviewDTO);

    @Named("toReviewDTOs")
    @IterableMapping(qualifiedByName = "toReviewDTO")
    List<ReviewDTO> toReviewDTOs(List<Review> reviews);

    @Named("toReviewEntities")
    @IterableMapping(qualifiedByName = "toReviewEntity")
    List<Review> toReviewEntities(List<ReviewDTO> reviewDTOs);
}
