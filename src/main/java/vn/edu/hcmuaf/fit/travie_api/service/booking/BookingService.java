package vn.edu.hcmuaf.fit.travie_api.service.booking;

import vn.edu.hcmuaf.fit.travie_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.dto.booking.BookingDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.booking.BookingRequest;

public interface BookingService {
    BookingDTO bookRoom(BookingRequest bookingRequest) throws BaseException;
}
