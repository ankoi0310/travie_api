package vn.edu.hcmuaf.fit.travie_api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.travie_api.core.entity.BaseEntity;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.TimeUnit;

import java.time.LocalTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "booking_type")
public class BookingType extends BaseEntity {
    private String name;
    private String description;

    @Temporal(TemporalType.TIME)
    private LocalTime startTime;

    @Temporal(TemporalType.TIME)
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    private TimeUnit unit;

    @ManyToOne
    private Hotel hotel;
}
