package vn.edu.hcmuaf.fit.travie_api.service.impl;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.fit.travie_api.core.exception.*;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.invoice.BookingStatus;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.invoice.PaymentStatus;
import vn.edu.hcmuaf.fit.travie_api.core.shared.utils.AppUtil;
import vn.edu.hcmuaf.fit.travie_api.core.shared.utils.PayOSUtil;
import vn.edu.hcmuaf.fit.travie_api.dto.booking.BookingRequest;
import vn.edu.hcmuaf.fit.travie_api.dto.invoice.InvoiceDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.payment.payos.LinkCreationResponse;
import vn.edu.hcmuaf.fit.travie_api.dto.payment.payos.PayOSResponse;
import vn.edu.hcmuaf.fit.travie_api.entity.*;
import vn.edu.hcmuaf.fit.travie_api.repository.*;
import vn.edu.hcmuaf.fit.travie_api.service.BookingService;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    @Value("${payos.url}")
    public String PAYOS_API_URL;

    @Value("${payos.client-id}")
    public String PAYOS_CLIENT_ID;

    @Value("${payos.api-key}")
    public String PAYOS_API_KEY;

    @Value("${payos.checksum-key}")
    public String PAYOS_CHECKSUM_KEY;

    @Value("${payos.return-url}")
    public String PAYOS_RETURN_URL;

    @Value("${payos.cancel-url}")
    public String PAYOS_CANCEL_URL;

    private final InvoiceRepository invoiceRepository;
    private final BookingTypeRepository bookingTypeRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Override
    public LinkCreationResponse createBooking(BookingRequest bookingRequest) throws BaseException, IOException {
        try {
            String username = AppUtil.getCurrentUsername();
            AppUser appUser = userRepository.findByUsername(username)
                                            .orElseThrow(() -> new NotFoundException("Không tìm thấy tài khoản"));

            Room room = roomRepository.findById(bookingRequest.getRoom().getId())
                                      .orElseThrow(() -> new BadRequestException("Phòng không tồn tại"));

            BookingType bookingType = bookingTypeRepository.findById(bookingRequest.getBookingType().getId())
                                                           .orElseThrow(() -> new BadRequestException("Loại đặt phòng" +
                                                                   " không tồn tại"));

            Invoice newInvoice = Invoice.builder().room(room).bookingType(bookingType)
                                        .checkIn(bookingRequest.getCheckIn()).checkOut(bookingRequest.getCheckOut())
                                        .totalPrice(bookingRequest.getTotalPrice())
                                        .finalPrice(bookingRequest.getFinalPrice())
                                        .bookingStatus(BookingStatus.PENDING).paymentStatus(PaymentStatus.UNPAID)
                                        .build();

            invoiceRepository.save(newInvoice);

            return createPaymentLink(newInvoice);
        } catch (IOException | BaseException e) {
            log.error(e);
            throw e;
        } catch (Exception e) {
            log.error(e);
            throw new ServiceUnavailableException("Lỗi khi tạo link thanh toán");
        }
    }

    private LinkCreationResponse createPaymentLink(Invoice invoice) throws IOException, BaseException {
        try {
            int orderCode = invoice.getCode();
            int amount = invoice.getFinalPrice();
            String description = "" + invoice.getCode();
            String cancelUrl = PAYOS_CANCEL_URL;
            String returnUrl = PAYOS_RETURN_URL;
            String signature = PayOSUtil.generateSignature(amount, cancelUrl, description, orderCode, returnUrl,
                    PAYOS_CHECKSUM_KEY);

            JsonObject json = new JsonObject();
            json.addProperty("orderCode", orderCode);
            json.addProperty("amount", amount);
            json.addProperty("description", description);
            json.addProperty("cancelUrl", cancelUrl);
            json.addProperty("returnUrl", returnUrl);
            json.addProperty("signature", signature);

            String response = Request.post(PAYOS_API_URL + "/v2/payment-requests")
                                     .addHeader("Content-Type", "application/json")
                                     .addHeader("x-client-id", PAYOS_CLIENT_ID)
                                     .addHeader("x-api-key", PAYOS_API_KEY)
                                     .bodyString(json.toString(),
                                             ContentType.APPLICATION_JSON.withCharset(StandardCharsets.UTF_8))
                                     .execute().returnContent().asString(StandardCharsets.UTF_8);

            Gson gson = new GsonBuilder().create();

            Type type = new TypeToken<PayOSResponse<LinkCreationResponse>>() {
            }.getType();
            PayOSResponse<LinkCreationResponse> payOSResponse = gson.fromJson(response, type);

            if (payOSResponse == null) {
                throw new BadRequestException("Tạo link thanh toán thất bại");
            }

            if (!Objects.equals(payOSResponse.getCode(), "00")) {
                throw new BadRequestException(payOSResponse.getDesc());
            }

            return payOSResponse.getData();
        } catch (IOException | BadRequestException e) {
            log.error(e);
            throw e;
        } catch (Exception e) {
            log.error(e);
            throw new ServiceUnavailableException("Lỗi khi tạo link thanh toán");
        }
    }

    @Override
    public InvoiceDTO cancelBooking(int code) throws BaseException {
        return null;
    }

    @Override
    public InvoiceDTO completePayment(int code) throws BaseException {
        return null;
    }
}
