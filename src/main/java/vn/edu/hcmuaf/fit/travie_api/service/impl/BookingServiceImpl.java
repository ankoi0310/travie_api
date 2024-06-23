package vn.edu.hcmuaf.fit.travie_api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.fit.travie_api.core.exception.BadRequestException;
import vn.edu.hcmuaf.fit.travie_api.core.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.BookingStatus;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.PaymentStatus;
import vn.edu.hcmuaf.fit.travie_api.dto.booking.BookingDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.booking.BookingRequest;
import vn.edu.hcmuaf.fit.travie_api.entity.*;
import vn.edu.hcmuaf.fit.travie_api.mapper.BookingMapper;
import vn.edu.hcmuaf.fit.travie_api.repository.*;
import vn.edu.hcmuaf.fit.travie_api.service.BookingService;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final BookingTypeRepository bookingTypeRepository;
    private final RoomRepository roomRepository;

    private final BookingMapper bookingMapper;

    @Override
    public BookingDTO bookRoom(BookingRequest bookingRequest) throws BaseException {
        Room room = roomRepository.findById(bookingRequest.getRoom().getId())
                                  .orElseThrow(() -> new BadRequestException("Phòng không tồn tại"));

        BookingType bookingType = bookingTypeRepository.findById(bookingRequest.getType().getId())
                                                       .orElseThrow(() -> new BadRequestException("Loại đặt phòng " +
                                                               "không tồn tại"));

        // TODO: check room is available and booking type
        int totalPrice = 0;
        Invoice newInvoice = Invoice.builder().room(room).type(bookingType).duration(bookingRequest.getDuration())
                                    .checkIn(bookingRequest.getCheckIn()).checkOut(bookingRequest.getCheckOut())
                                    .price(totalPrice).total(totalPrice)
                                    .bookingStatus(BookingStatus.PENDING).paymentStatus(PaymentStatus.UNPAID).build();

        bookingRepository.save(newInvoice);

        return bookingMapper.toDTO(newInvoice);
    }

    @Override
    public void changeBookingStatus(long id, BookingStatus status) throws BaseException {
        Invoice invoice = bookingRepository.findById(id)
                                           .orElseThrow(() -> new BadRequestException("Không tìm thấy lịch sử " +
                                                   "đặt" + " phòng"));

        switch (invoice.getBookingStatus()) {
            case CANCELLED:
            case COMPLETED:
            case REJECTED:
                throw new BadRequestException("Không thể thay đổi trạng thái hiện tại");
            case CHECKED_IN:
                if (status != BookingStatus.COMPLETED) {
                    throw new BadRequestException("Không thể thay đổi trạng thái hiện tại");
                }
                break;
            case CONFIRMED:
                if (status != BookingStatus.CANCELLED && status != BookingStatus.CHECKED_IN && status != BookingStatus.REJECTED) {
                    throw new BadRequestException("Không thể thay đổi trạng thái hiện tại");
                }
                break;
            case PENDING: // it could cancel
                if (status != BookingStatus.CANCELLED && status != BookingStatus.CONFIRMED && status != BookingStatus.REJECTED) {
                    throw new BadRequestException("Không thể thay đổi trạng thái hiện tại");
                }
                break;
        }

        invoice.setBookingStatus(status);
        bookingRepository.save(invoice);
    }
}
