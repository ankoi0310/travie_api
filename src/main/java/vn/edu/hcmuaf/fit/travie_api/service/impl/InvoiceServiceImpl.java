package vn.edu.hcmuaf.fit.travie_api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.fit.travie_api.core.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.core.exception.NotFoundException;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.invoice.BookingStatus;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.invoice.PaymentStatus;
import vn.edu.hcmuaf.fit.travie_api.dto.invoice.InvoiceDTO;
import vn.edu.hcmuaf.fit.travie_api.entity.AppUser;
import vn.edu.hcmuaf.fit.travie_api.entity.Invoice;
import vn.edu.hcmuaf.fit.travie_api.mapper.InvoiceMapper;
import vn.edu.hcmuaf.fit.travie_api.repository.InvoiceRepository;
import vn.edu.hcmuaf.fit.travie_api.service.InvoiceService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;

    @Override
    public List<InvoiceDTO> getAllInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        return invoiceMapper.toInvoiceDTOs(invoices);
    }

    @Override
    public List<InvoiceDTO> getInvoicesByUser(AppUser user) {
        List<Invoice> invoices = invoiceRepository.findByUser(user);
        return invoiceMapper.toInvoiceDTOs(invoices);
    }

    @Override
    public InvoiceDTO getInvocieById(Long id) throws BaseException {
        Invoice invoice = invoiceRepository.findById(id)
                                           .orElseThrow(() -> new NotFoundException("Không tìm thấy hóa đơn"));
        return invoiceMapper.toInvoiceDTO(invoice);
    }

    @Scheduled(fixedDelay = 60000, initialDelay = 0) // 1 minute
    public void updateInvoiceStatus() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        LocalDateTime currentTime = LocalDateTime.now();
        String formattedTime = currentTime.format(formatter);
        List<Invoice> invoices = invoiceRepository.findAllPendingInvoices();
        invoices.forEach(invoice -> {
            if (invoice.getCreatedDate().plusMinutes(1).isBefore(currentTime)) {
                invoice.setBookingStatus(BookingStatus.CANCELLED);
                invoice.setPaymentStatus(PaymentStatus.FAILED);
                String message = String.format("Updated invoice code %s from PENDING to CANCELLED at %s",
                        invoice.getCode(), formattedTime);
                log.info(message);
            }
        });
        invoiceRepository.saveAll(invoices);
    }
}
