package vn.edu.hcmuaf.fit.travie_api.repository.booking;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.travie_api.core.repository.CustomRepository;
import vn.edu.hcmuaf.fit.travie_api.entity.Booking;

@NoRepositoryBean
public interface BookingRepository extends CustomRepository<Booking, Long> {
}
