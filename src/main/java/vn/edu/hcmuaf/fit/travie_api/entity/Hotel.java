package vn.edu.hcmuaf.fit.travie_api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.travie_api.core.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hotel")
public class Hotel extends BaseEntity {
    private String name;
    private String description;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private Address address;

    private double rating;

    @OneToMany(mappedBy = "hotel")
    private List<Room> rooms;

//    @OneToMany(mappedBy = "hotel")
//    private List<Review> reviews;

//    @OneToMany(mappedBy = "hotel")
//    private List<HotelImage> hotelImages;

//    @ManyToMany
//    @JoinTable(
//            name = "hotel_booking_type",
//            joinColumns = @JoinColumn(name = "hotel_id"),
//            inverseJoinColumns = @JoinColumn(name = "booking_type_id")
//    )
//    private List<BookingType> bookingTypes;

//    @OneToMany(mappedBy = "hotel")
//    private List<Booking> bookings;

    public void addRoom(Room room) {
        if (rooms == null) {
            rooms = new ArrayList<>();
        }

        rooms.add(room);
        room.setHotel(this);
    }

//    public double getRating() {
//        return reviews.stream()
//                      .mapToDouble(Review::getRating)
//                      .average()
//                      .orElse(0);
//    }

    public List<Facility> getFacilities() {
        return rooms.stream()
                    .map(Room::getFacilities)
                    .flatMap(List::stream)
                    .distinct()
                    .collect(Collectors.toList());
    }
}
