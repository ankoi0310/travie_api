package vn.edu.hcmuaf.fit.travie_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.travie_api.core.handler.HttpResponse;
import vn.edu.hcmuaf.fit.travie_api.dto.review.ReviewDTO;
import vn.edu.hcmuaf.fit.travie_api.service.ReviewService;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<HttpResponse> getReviews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ReviewDTO> reviews = reviewService.getReviews(page, size);
        return ResponseEntity.ok(HttpResponse.success(reviews, "Lấy danh sách đánh giá thành công"));
    }
}
