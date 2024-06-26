package vn.edu.hcmuaf.fit.travie_api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.travie_api.core.entity.BaseEntity;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.invoice.*;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoice")
public class Invoice extends BaseEntity {
    private int code;

    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;

    private LocalDateTime checkIn;
    private LocalDateTime checkOut;

    @ManyToOne
    private BookingType bookingType;

    @ManyToOne
    private AppUser user;

    private String guestName;
    private String guestPhone;
    private int totalPrice;
    private int finalPrice;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}
