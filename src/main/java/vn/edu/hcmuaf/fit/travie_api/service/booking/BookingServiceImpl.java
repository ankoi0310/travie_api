package vn.edu.hcmuaf.fit.travie_api.service.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.fit.travie_api.core.handler.exception.*;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.BookingStatus;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.PaymentStatus;
import vn.edu.hcmuaf.fit.travie_api.dto.booking.BookingDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.booking.BookingRequest;
import vn.edu.hcmuaf.fit.travie_api.entity.*;
import vn.edu.hcmuaf.fit.travie_api.mapper.BookingMapper;
import vn.edu.hcmuaf.fit.travie_api.repository.booking.BookingRepository;
import vn.edu.hcmuaf.fit.travie_api.repository.bookingtype.BookingTypeRepository;
import vn.edu.hcmuaf.fit.travie_api.repository.room.RoomRepository;

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
        try {
            Room room = roomRepository.findById(bookingRequest.getRoom().getId())
                                      .orElseThrow(() -> new BadRequestException("Phòng không tồn tại"));

            BookingType bookingType = bookingTypeRepository.findById(bookingRequest.getType().getId())
                                                           .orElseThrow(() -> new BadRequestException("Loại đặt phòng" +
                                                                   " không tồn tại"));

            int price = room.getPrice() * bookingRequest.getDuration();
            Booking newBooking = Booking.builder()
                                        .room(room)
                                        .type(bookingType)
                                        .duration(bookingRequest.getDuration())
                                        .checkIn(bookingRequest.getCheckIn())
                                        .checkOut(bookingRequest.getCheckOut())
                                        .price(room.getPrice() * bookingRequest.getDuration())
                                        .total(price)
                                        .bookingStatus(BookingStatus.PENDING)
                                        .paymentStatus(PaymentStatus.UNPAID)
                                        .build();

            bookingRepository.save(newBooking);

            return bookingMapper.toDTO(newBooking);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error when create booking", e);
            throw new ServiceBusinessException("Xảy ra lỗi khi đặt phòng");
        }
    }
}
