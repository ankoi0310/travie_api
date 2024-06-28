package vn.edu.hcmuaf.fit.travie_api.service;

import vn.edu.hcmuaf.fit.travie_api.core.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.dto.invoice.InvoiceDTO;
import vn.edu.hcmuaf.fit.travie_api.entity.AppUser;

import java.util.List;

public interface InvoiceService {
    List<InvoiceDTO> getAllInvoices();

    List<InvoiceDTO> getInvoicesByUser(AppUser user);

    InvoiceDTO getInvocieById(Long id) throws BaseException;
}
