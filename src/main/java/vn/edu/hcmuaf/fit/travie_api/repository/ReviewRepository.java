package vn.edu.hcmuaf.fit.travie_api.repository;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.travie_api.core.repository.CustomRepository;
import vn.edu.hcmuaf.fit.travie_api.entity.Review;

@NoRepositoryBean
public interface ReviewRepository extends CustomRepository<Review, Long> {
    Page<Review> findAll(BooleanBuilder predicate, Pageable pageable);

    Page<Review> findByRating(int rating, Pageable pageable);
}
