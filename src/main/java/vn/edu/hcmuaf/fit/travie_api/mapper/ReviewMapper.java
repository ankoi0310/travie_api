package vn.edu.hcmuaf.fit.travie_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.travie_api.dto.review.ReviewDTO;
import vn.edu.hcmuaf.fit.travie_api.entity.Review;

import java.util.List;

@Mapper
public interface ReviewMapper {
    @Named("toReviewDTO")
    @Mapping(target = "user.nickname", source = "user.nickname")
    @Mapping(target = "user.avatar", source = "user.avatar")
    ReviewDTO toDTO(Review review);

    @Named("toReviewEntity")
    @Mapping(target = "user.nickname", source = "user.nickname")
    @Mapping(target = "user.avatar", source = "user.avatar")
    Review toEntity(ReviewDTO reviewDTO);

    @Named("toReviewDTOs")
    @IterableMapping(qualifiedByName = "toReviewDTO")
    List<ReviewDTO> toDTOs(List<Review> reviews);

    @Named("toReviewEntities")
    @IterableMapping(qualifiedByName = "toReviewEntity")
    List<Review> toEntities(List<ReviewDTO> reviewDTOs);
}
