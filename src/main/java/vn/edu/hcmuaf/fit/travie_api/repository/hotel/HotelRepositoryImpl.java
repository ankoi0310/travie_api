package vn.edu.hcmuaf.fit.travie_api.repository.hotel;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.travie_api.core.repository.AbstractRepository;
import vn.edu.hcmuaf.fit.travie_api.entity.Hotel;
import vn.edu.hcmuaf.fit.travie_api.entity.QHotel;

import java.util.Optional;

@Repository
public class HotelRepositoryImpl extends AbstractRepository<Hotel, Long> implements HotelRepository {
    private final QHotel qHotel = QHotel.hotel;

    protected HotelRepositoryImpl(EntityManager entityManager) {
        super(Hotel.class, entityManager);
    }


    @Override
    public Optional<Hotel> findByName(String name) {
        return Optional.ofNullable(queryFactory
                .selectFrom(qHotel)
                .where(qHotel.name.eq(name))
                .fetchOne());
    }
}
