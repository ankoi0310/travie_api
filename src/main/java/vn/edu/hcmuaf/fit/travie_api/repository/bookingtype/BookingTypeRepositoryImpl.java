package vn.edu.hcmuaf.fit.travie_api.repository.bookingtype;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.travie_api.core.repository.AbstractRepository;
import vn.edu.hcmuaf.fit.travie_api.entity.BookingType;
import vn.edu.hcmuaf.fit.travie_api.entity.QBookingType;

@Repository
public class BookingTypeRepositoryImpl extends AbstractRepository<BookingType, Long> implements BookingTypeRepository {
    private final QBookingType qBookingType = QBookingType.bookingType;

    protected BookingTypeRepositoryImpl(EntityManager entityManager) {
        super(BookingType.class, entityManager);
    }
}
