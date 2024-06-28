package vn.edu.hcmuaf.fit.travie_api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.fit.travie_api.dto.review.ReviewDTO;
import vn.edu.hcmuaf.fit.travie_api.entity.Review;
import vn.edu.hcmuaf.fit.travie_api.mapper.ReviewMapper;
import vn.edu.hcmuaf.fit.travie_api.repository.ReviewRepository;
import vn.edu.hcmuaf.fit.travie_api.service.ReviewService;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public Page<ReviewDTO> getReviews(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Review> reviews = reviewRepository.findAll(pageable);
        return reviews.map(reviewMapper::toDTO);
    }
}
