package vn.edu.hcmuaf.fit.travie_api.service;

import vn.edu.hcmuaf.fit.travie_api.core.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.BookingStatus;
import vn.edu.hcmuaf.fit.travie_api.dto.booking.BookingDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.booking.BookingRequest;

public interface BookingService {
    BookingDTO bookRoom(BookingRequest bookingRequest) throws BaseException;

    void changeBookingStatus(long id, BookingStatus status) throws BaseException;
}
