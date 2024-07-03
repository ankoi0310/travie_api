package vn.edu.hcmuaf.fit.travie_api.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import jakarta.persistence.EntityManager;
import lombok.NonNull;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.travie_api.core.repository.AbstractRepository;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.hotel.HotelStatus;
import vn.edu.hcmuaf.fit.travie_api.dto.hotel.HotelSearch;
import vn.edu.hcmuaf.fit.travie_api.entity.Hotel;
import vn.edu.hcmuaf.fit.travie_api.entity.QHotel;
import vn.edu.hcmuaf.fit.travie_api.repository.HotelRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class HotelRepositoryImpl extends AbstractRepository<Hotel, Long> implements HotelRepository {
    private final QHotel qHotel = QHotel.hotel;

    protected HotelRepositoryImpl(EntityManager entityManager) {
        super(Hotel.class, entityManager);
    }


    @Override
    public @NonNull Page<Hotel> findAll(BooleanBuilder predicate, @NonNull Pageable pageable) {
        OrderSpecifier<?>[] orderSpecifiers = getOrderSpecifiers(pageable.getSort());

        List<Hotel> hotels = queryFactory.selectFrom(qHotel)
                                         .where(predicate)
                                         .orderBy(orderSpecifiers)
                                         .orderBy(qHotel.id.asc())
                                         .limit(pageable.getPageSize())
                                         .offset(pageable.getOffset())
                                         .fetch();

        return new PageImpl<>(hotels, pageable, hotels.size());
    }

    @Override
    public Page<Hotel> search(HotelSearch hotelSearch, Pageable pageable) {
        BooleanBuilder predicate = buildPredicate(hotelSearch);
        return findAll(predicate, pageable);
    }

    @Override
    public Page<Hotel> findNearbyHotels(String location, Pageable pageable) {
        List<Hotel> hotels = queryFactory.selectFrom(qHotel)
                                         .where(qHotel.address.fullAddress.containsIgnoreCase(location))
                                         .limit(pageable.getPageSize())
                                         .offset(pageable.getOffset())
                                         .fetch();
        return new PageImpl<>(hotels, pageable, hotels.size());
    }

    @Override
    public Page<Hotel> findByOrderByAverageMarkDesc(Pageable pageable) {
        List<Hotel> hotels = queryFactory.selectFrom(qHotel)
                                         .where(qHotel.averageMark.isNotNull()
                                                                  .and(qHotel.status.eq(HotelStatus.ACTIVE)))
                                         .orderBy(qHotel.averageMark.desc())
                                         .limit(pageable.getPageSize())
                                         .offset(pageable.getOffset())
                                         .fetch();
        return new PageImpl<>(hotels, pageable, hotels.size());
    }

    @Override
    public Page<Hotel> findByOrderByCreatedDateDesc(Pageable pageable) {
        List<Hotel> hotels = queryFactory.selectFrom(qHotel)
                                         .orderBy(qHotel.createdDate.desc())
                                         .limit(pageable.getPageSize())
                                         .offset(pageable.getOffset())
                                         .fetch();
        return new PageImpl<>(hotels, pageable, hotels.size());
    }

    @Override
    public Optional<Hotel> findByName(String name) {
        return Optional.ofNullable(queryFactory
                .selectFrom(qHotel)
                .where(qHotel.name.eq(name))
                .fetchOne());
    }

    private BooleanBuilder buildPredicate(HotelSearch hotelSearch) {
        BooleanBuilder predicate = new BooleanBuilder();

        if (hotelSearch.getKeyword() != null) {
            BooleanExpression namePredicate = qHotel.name.lower()
                                                         .containsIgnoreCase(hotelSearch.getKeyword().toLowerCase());
//            BooleanExpression addressPredicate =
//                    qHotel.address.fullAddress.containsIgnoreCase(hotelSearch.getKeyword());
//            predicate.and(namePredicate.or(addressPredicate));
            predicate.and(namePredicate);
        }

        return predicate;
    }

    private OrderSpecifier<?>[] getOrderSpecifiers(Sort sort) {
        return sort.stream()
                   .map(order -> new OrderSpecifier<>(
                           order.isAscending() ? Order.ASC : Order.DESC,
                           Expressions.stringPath(order.getProperty())))
                   .toArray(OrderSpecifier[]::new);
    }
}
