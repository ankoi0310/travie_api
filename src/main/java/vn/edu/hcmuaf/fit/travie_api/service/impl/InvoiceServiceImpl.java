package vn.edu.hcmuaf.fit.travie_api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.fit.travie_api.core.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.core.exception.NotFoundException;
import vn.edu.hcmuaf.fit.travie_api.dto.invoice.InvoiceDTO;
import vn.edu.hcmuaf.fit.travie_api.entity.AppUser;
import vn.edu.hcmuaf.fit.travie_api.entity.Invoice;
import vn.edu.hcmuaf.fit.travie_api.mapper.InvoiceMapper;
import vn.edu.hcmuaf.fit.travie_api.repository.InvoiceRepository;
import vn.edu.hcmuaf.fit.travie_api.service.InvoiceService;

import java.util.List;

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
}
