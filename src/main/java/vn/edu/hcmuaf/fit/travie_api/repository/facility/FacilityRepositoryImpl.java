package vn.edu.hcmuaf.fit.travie_api.repository.facility;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.travie_api.core.repository.AbstractRepository;
import vn.edu.hcmuaf.fit.travie_api.entity.*;

import java.util.List;
import java.util.Optional;

@Repository
public class FacilityRepositoryImpl extends AbstractRepository<Facility, Long> implements FacilityRepository {
    private final QFacility qFacility = QFacility.facility;
    private final QRoom qRoom = QRoom.room;

    protected FacilityRepositoryImpl(EntityManager entityManager) {
        super(Facility.class, entityManager);
    }

    @Override
    public List<Facility> findAllByRooms(List<Room> rooms) {
        return queryFactory
                .selectFrom(qFacility)
                .innerJoin(qFacility.rooms, qRoom)
                .where(qRoom.in(rooms))
                .fetch();
    }

    @Override
    public Optional<Facility> findByName(String name) {
        return Optional.ofNullable(queryFactory
                .selectFrom(qFacility)
                .where(qFacility.name.eq(name))
                .fetchOne()
        );
    }
}
