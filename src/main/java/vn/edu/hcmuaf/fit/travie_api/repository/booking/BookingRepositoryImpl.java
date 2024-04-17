package vn.edu.hcmuaf.fit.travie_api.repository.booking;

import jakarta.persistence.EntityManager;
import lombok.NonNull;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.travie_api.core.repository.AbstractRepository;
import vn.edu.hcmuaf.fit.travie_api.entity.*;

@Repository
public class BookingRepositoryImpl extends AbstractRepository<Booking, Long> implements BookingRepository {
    private final QBooking qBooking = QBooking.booking;
    private final QBookingType qBookingType = QBookingType.bookingType;

    protected BookingRepositoryImpl(EntityManager entityManager) {
        super(Booking.class, entityManager);
    }

    @Override
    public <S extends Booking> @NonNull S save(@NonNull S entity) {
        return super.save(entity);
    }
}
