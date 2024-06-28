package vn.edu.hcmuaf.fit.travie_api.service;

import org.springframework.data.domain.Page;
import vn.edu.hcmuaf.fit.travie_api.dto.review.ReviewDTO;

public interface ReviewService {
    Page<ReviewDTO> getReviews(int page, int size);
}
