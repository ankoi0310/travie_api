package vn.edu.hcmuaf.fit.travie_api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.travie_api.core.entity.BaseEntity;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "room")
public class Room extends BaseEntity {
    private String name;
    private int numOfRooms;

    private int firstHoursOrigin;
    private int minNumHours;
    private int maxNumHours;

    private int oneDayOrigin;

    private int overnightOrigin;

    private int additionalHours;
    private int additionalOrigin;

    @Column(columnDefinition = "int default 0")
    private boolean hasDiscount;

    @Column(columnDefinition = "int default 0")
    private boolean applyFlashSale;

    @Column(columnDefinition = "int default 0")
    private int priceFlashSale;

    @Column(columnDefinition = "int default 0")
    private boolean hasExtraFee;

    @Column(columnDefinition = "int default 1")
    private boolean available;

    @Column(columnDefinition = "int default 1")
    private boolean availableTonight;

    @Column(columnDefinition = "int default 1")
    private boolean availableTomorrow;

    @Column(columnDefinition = "int default 0")
    private boolean soldOut;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "room_amenity",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private List<Amenity> amenities;

    @OneToMany(mappedBy = "room")
    private List<RoomImage> roomImages;

    @Column(columnDefinition = "int default 1")
    private int status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    public List<String> getImages() {
        return roomImages.stream().map(RoomImage::getImage).collect(Collectors.toList());
    }
}
