package vn.edu.hcmuaf.fit.travie_api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.travie_api.core.entity.BaseEntity;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.hotel.HotelStatus;

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

    @Column(columnDefinition = "text")
    private String introduction;

    private int firstHours; // 1

    private int checkIn;
    private int checkOut;
    private boolean daily;

    private int startHourly;
    private int endHourly;
    private boolean hourly;

    private int startOvernight;
    private int endOvernight;
    private boolean overnight;

    private int cancelBeforeHours; // 1

    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    private Address address;

    @OneToMany(mappedBy = "hotel")
    private List<BookingType> bookingTypes;

    @OneToMany(mappedBy = "hotel")
    private List<Room> rooms;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(10) default 'ACTIVE'")
    private HotelStatus status;

    @Column(columnDefinition = "double default 0")
    private double averageMark;

    @Column(columnDefinition = "double default 0")
    private double averageMarkClean;

    @Column(columnDefinition = "double default 0")
    private double averageMarkAmenity;

    @Column(columnDefinition = "double default 0")
    private double averageMarkService;

    @OneToMany(mappedBy = "hotel")
    private List<Review> reviews;

    public void addRoom(Room room) {
        if (rooms == null) {
            rooms = new ArrayList<>();
        }

        rooms.add(room);
        room.setHotel(this);
    }

    public double getRating() {
        return reviews.stream()
                      .mapToDouble(Review::getRating)
                      .average()
                      .orElse(0);
    }

    public List<String> getImages() {
        return rooms.stream()
                    .map(Room::getRoomImages)
                    .flatMap(List::stream)
                    .map(RoomImage::getImage)
                    .collect(Collectors.toList());
    }

    public List<Amenity> getAmenities() {
        return rooms.stream()
                    .map(Room::getAmenities)
                    .flatMap(List::stream)
                    .distinct()
                    .collect(Collectors.toList());
    }

    public void addReview(Review review) {
        if (reviews == null) {
            reviews = new ArrayList<>();
        }

        reviews.add(review);
        review.setHotel(this);
    }
}
