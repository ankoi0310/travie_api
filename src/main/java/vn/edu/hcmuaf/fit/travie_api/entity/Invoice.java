package vn.edu.hcmuaf.fit.travie_api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.travie_api.core.entity.BaseEntity;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.BookingStatus;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.PaymentStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "booking")
public class Invoice extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    private BookingType type;

    private Integer duration;

    private LocalDateTime checkIn;

    private LocalDateTime checkOut;

//    @ManyToOne(fetch = FetchType.LAZY)
//    private AppUser user;

    private String phone;

    private String guestName;

    @Column(nullable = false)
    private int price;

    private int discount;

    private int total;

    private String note;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}
