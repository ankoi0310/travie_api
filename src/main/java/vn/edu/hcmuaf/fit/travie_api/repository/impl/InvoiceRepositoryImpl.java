package vn.edu.hcmuaf.fit.travie_api.repository.impl;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.travie_api.core.repository.AbstractRepository;
import vn.edu.hcmuaf.fit.travie_api.entity.Invoice;
import vn.edu.hcmuaf.fit.travie_api.repository.BookingRepository;

@Repository
public class InvoiceRepositoryImpl extends AbstractRepository<Invoice, Long> implements BookingRepository {
    protected InvoiceRepositoryImpl(EntityManager entityManager) {
        super(Invoice.class, entityManager);
    }

}
