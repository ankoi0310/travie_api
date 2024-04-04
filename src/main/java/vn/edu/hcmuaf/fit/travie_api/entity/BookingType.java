package vn.edu.hcmuaf.fit.travie_api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.travie_api.core.entity.BaseEntity;

import java.sql.Time;
import java.util.List;

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
    private Time checkInTime;

    @Temporal(TemporalType.TIME)
    private Time checkOutTime;
    private boolean isHourly;
    private boolean isByDay;
    private boolean isOvernight;

    @ManyToMany
    @JoinTable(
            name = "hotel_booking_type",
            joinColumns = @JoinColumn(name = "booking_type_id"),
            inverseJoinColumns = @JoinColumn(name = "hotel_id")
    )
    private List<Hotel> hotels;
}
