package vn.edu.hcmuaf.fit.travie_api.repository.impl;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.travie_api.core.repository.AbstractRepository;
import vn.edu.hcmuaf.fit.travie_api.entity.*;
import vn.edu.hcmuaf.fit.travie_api.repository.AmenityRepository;

import java.util.Optional;

@Repository
public class AmenityRepositoryImpl extends AbstractRepository<Amenity, Long> implements AmenityRepository {
    private final QAmenity qAmenity = QAmenity.amenity;
    private final QRoom qRoom = QRoom.room;

    protected AmenityRepositoryImpl(EntityManager entityManager) {
        super(Amenity.class, entityManager);
    }

    @Override
    public Optional<Amenity> findByName(String name) {
        return Optional.ofNullable(queryFactory
                .selectFrom(qAmenity)
                .where(qAmenity.name.eq(name).and(qAmenity.deleted.isFalse()))
                .fetchOne()
        );
    }
}
