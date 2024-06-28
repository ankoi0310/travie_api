package vn.edu.hcmuaf.fit.travie_api.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.travie_api.core.repository.AbstractRepository;
import vn.edu.hcmuaf.fit.travie_api.entity.QReview;
import vn.edu.hcmuaf.fit.travie_api.entity.Review;
import vn.edu.hcmuaf.fit.travie_api.repository.ReviewRepository;

import java.util.List;

@Repository
public class ReviewRepositoryImpl extends AbstractRepository<Review, Long> implements ReviewRepository {
    private final QReview qReview = QReview.review;

    protected ReviewRepositoryImpl(EntityManager entityManager) {
        super(Review.class, entityManager);
    }


    @Override
    public Page<Review> findAll(BooleanBuilder predicate, Pageable pageable) {
        OrderSpecifier<?>[] orderSpecifiers = getOrderSpecifiers(pageable.getSort());

        List<Review> reviews = queryFactory.selectFrom(qReview)
                                           .where(predicate)
                                           .orderBy(orderSpecifiers)
                                           .orderBy(qReview.id.asc())
                                           .limit(pageable.getPageSize())
                                           .offset(pageable.getOffset())
                                           .fetch();

        return new PageImpl<>(reviews, pageable, reviews.size());
    }

    @Override
    public Page<Review> findByRating(int rating, Pageable pageable) {
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(qReview.rating.eq(rating));
        return findAll(predicate, pageable);
    }

    private OrderSpecifier<?>[] getOrderSpecifiers(Sort sort) {
        return sort.stream()
                   .map(order -> new OrderSpecifier<>(
                           order.isAscending() ? Order.ASC : Order.DESC,
                           Expressions.stringPath(order.getProperty())))
                   .toArray(OrderSpecifier[]::new);
    }
}
