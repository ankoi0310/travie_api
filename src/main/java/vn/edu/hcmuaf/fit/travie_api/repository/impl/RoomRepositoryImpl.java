package vn.edu.hcmuaf.fit.travie_api.repository.impl;

import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.travie_api.core.repository.AbstractRepository;
import vn.edu.hcmuaf.fit.travie_api.dto.room.RoomSearch;
import vn.edu.hcmuaf.fit.travie_api.entity.*;
import vn.edu.hcmuaf.fit.travie_api.repository.RoomRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class RoomRepositoryImpl extends AbstractRepository<Room, Long> implements RoomRepository {
    private final QRoom qRoom = QRoom.room;
    private final QAmenity qAmenity = QAmenity.amenity;

    protected RoomRepositoryImpl(EntityManager entityManager) {
        super(Room.class, entityManager);
    }

    @Override
    public List<Room> search(RoomSearch roomSearch) {
        return queryFactory.selectFrom(qRoom)
                           .leftJoin(qRoom.amenities, qAmenity)
                           .fetchJoin()
                           .where(buildSearchPredicate(roomSearch))
                           .fetch();
    }

    @Override
    public List<Room> findAllByHotelId(long hotelId) {
        return queryFactory.selectFrom(qRoom)
                           .where(qRoom.hotel.id.eq(hotelId))
                           .fetch();
    }

    @Override
    public List<Room> findAllByAmenities(List<Amenity> amenities) {
        return queryFactory.selectFrom(qRoom)
                           .where(qRoom.amenities.any().in(amenities))
                           .fetch();
    }

    @Override
    public Optional<Room> findByName(String name) {
        return Optional.ofNullable(queryFactory
                .selectFrom(qRoom)
                .where(qRoom.name.eq(name))
                .fetchOne());
    }

    private BooleanBuilder buildSearchPredicate(RoomSearch roomSearch) {
        BooleanBuilder predicate = new BooleanBuilder();

        if (roomSearch.getHotelId() != null) {
            predicate.and(qRoom.hotel.id.eq(roomSearch.getHotelId()));
        }

        if (roomSearch.getApplyFlashSale() != null) {
            predicate.and(qRoom.applyFlashSale.eq(roomSearch.getApplyFlashSale()));
        }

        return predicate;
    }
}
