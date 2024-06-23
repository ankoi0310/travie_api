package vn.edu.hcmuaf.fit.travie_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.travie_api.core.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.core.handler.HttpResponse;
import vn.edu.hcmuaf.fit.travie_api.dto.invoice.InvoiceDTO;
import vn.edu.hcmuaf.fit.travie_api.service.InvoiceService;

import java.util.List;

@RestController
@RequestMapping("/invoice")
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService invoiceService;

    @GetMapping
    public ResponseEntity<HttpResponse> getAllInvoices() {
        List<InvoiceDTO> invoices = invoiceService.getAllInvoices();
        return ResponseEntity.ok(HttpResponse.success(invoices, "Lấy danh sách hóa đơn thành công!"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> getInvoiceById(@PathVariable Long id) throws BaseException {
        InvoiceDTO invoice = invoiceService.getInvocieById(id);
        return ResponseEntity.ok(HttpResponse.success(invoice, "Lấy thông tin hóa đơn thành công!"));
    }
}
