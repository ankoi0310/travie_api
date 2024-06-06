package vn.edu.hcmuaf.fit.travie_api.repository;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.travie_api.core.repository.CustomRepository;
import vn.edu.hcmuaf.fit.travie_api.entity.Invoice;

@NoRepositoryBean
public interface BookingRepository extends CustomRepository<Invoice, Long> {
}
