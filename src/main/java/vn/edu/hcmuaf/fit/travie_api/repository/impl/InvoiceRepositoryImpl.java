package vn.edu.hcmuaf.fit.travie_api.repository.impl;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.travie_api.core.repository.AbstractRepository;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.invoice.BookingStatus;
import vn.edu.hcmuaf.fit.travie_api.entity.*;
import vn.edu.hcmuaf.fit.travie_api.repository.InvoiceRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class InvoiceRepositoryImpl extends AbstractRepository<Invoice, Long> implements InvoiceRepository {
    private final QInvoice qInvoice = QInvoice.invoice;

    protected InvoiceRepositoryImpl(EntityManager entityManager) {
        super(Invoice.class, entityManager);
    }

    @Override
    public List<Invoice> findByUser(AppUser user) {
        return queryFactory.selectFrom(qInvoice)
                           .where(qInvoice.user.eq(user))
                           .orderBy(qInvoice.createdDate.desc())
                           .fetch();
    }

    @Override
    public List<Invoice> findAllPendingInvoices() {
        return queryFactory.selectFrom(qInvoice)
                           .where(qInvoice.bookingStatus.eq(BookingStatus.PENDING))
                           .fetch();
    }

    @Override
    public Optional<Invoice> findByCode(int code) {
        return Optional.ofNullable(queryFactory.selectFrom(qInvoice)
                                               .where(qInvoice.code.eq(code))
                                               .fetchOne());
    }
}
