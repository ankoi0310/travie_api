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
@Table(name = "room_image")
public class RoomImage extends BaseEntity {
    private String image;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
}
