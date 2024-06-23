package vn.edu.hcmuaf.fit.travie_api.repository.impl;

import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.travie_api.core.repository.AbstractRepository;
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
    public List<Hotel> search(HotelSearch hotelSearch) {
        return queryFactory.selectFrom(qHotel)
                           .where(buildPredicate(hotelSearch))
                           .fetch();
    }

    @Override
    public List<Hotel> findNearbyHotels(String location) {
        return queryFactory.selectFrom(qHotel)
                           .where(qHotel.address.fullAddress.containsIgnoreCase(location))
                           .limit(5)
                           .fetch();
    }

    @Override
    public List<Hotel> findTop5ByOrderByAverageMarkDesc() {
        return queryFactory.selectFrom(qHotel)
                           .orderBy(qHotel.averageMark.desc())
                           .limit(5)
                           .fetch();
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
        return predicate;
    }
}
