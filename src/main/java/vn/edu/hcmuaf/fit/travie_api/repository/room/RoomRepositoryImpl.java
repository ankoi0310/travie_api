package vn.edu.hcmuaf.fit.travie_api.repository.room;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.travie_api.core.repository.AbstractRepository;
import vn.edu.hcmuaf.fit.travie_api.entity.*;

import java.util.List;
import java.util.Optional;

@Repository
public class RoomRepositoryImpl extends AbstractRepository<Room, Long> implements RoomRepository {
    private final QRoom qRoom = QRoom.room;

    protected RoomRepositoryImpl(EntityManager entityManager) {
        super(Room.class, entityManager);
    }

    @Override
    public List<Room> findAllByHotelId(Long hotelId) {
        return queryFactory.selectFrom(qRoom)
                           .where(qRoom.hotel.id.eq(hotelId))
                           .fetch();
    }

    @Override
    public List<Room> findAllByFacilities(List<Facility> facilities) {
        return queryFactory.selectFrom(qRoom)
                           .where(qRoom.facilities.any().in(facilities))
                           .fetch();
    }

    @Override
    public Optional<Room> findByName(String name) {
        return Optional.ofNullable(queryFactory
                .selectFrom(qRoom)
                .where(qRoom.name.eq(name))
                .fetchOne());
    }
}
