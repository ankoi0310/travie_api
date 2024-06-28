package vn.edu.hcmuaf.fit.travie_api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.travie_api.core.entity.BaseEntity;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hotel_review")
public class Review extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    private int rating;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
}
