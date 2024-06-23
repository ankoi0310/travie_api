package vn.edu.hcmuaf.fit.travie_api.repository;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.travie_api.core.repository.CustomRepository;
import vn.edu.hcmuaf.fit.travie_api.entity.AppUser;
import vn.edu.hcmuaf.fit.travie_api.entity.Invoice;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface InvoiceRepository extends CustomRepository<Invoice, Long> {
    List<Invoice> findByUser(AppUser user);

    Optional<Invoice> findByCode(int code);
}
