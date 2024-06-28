package vn.edu.hcmuaf.fit.travie_api.service;

import vn.edu.hcmuaf.fit.travie_api.core.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.dto.booking.BookingRequest;
import vn.edu.hcmuaf.fit.travie_api.dto.invoice.InvoiceDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.payment.payos.LinkCreationResponse;

import java.io.IOException;

public interface BookingService {
    LinkCreationResponse createBooking(BookingRequest bookingRequest) throws BaseException, IOException;

    InvoiceDTO cancelCheckout(int code) throws BaseException;

    InvoiceDTO completeCheckout(int code) throws BaseException;
}
