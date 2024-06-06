package vn.edu.hcmuaf.fit.travie_api.repository;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.travie_api.core.repository.CustomRepository;
import vn.edu.hcmuaf.fit.travie_api.entity.BookingType;

@NoRepositoryBean
public interface BookingTypeRepository extends CustomRepository<BookingType, Long> {
}
