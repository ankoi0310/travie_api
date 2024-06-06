package vn.edu.hcmuaf.fit.travie_api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.travie_api.core.entity.BaseEntity;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "room_type")
public class RoomType extends BaseEntity {
    private String name;
    private String description;

    // Mot loai phong co nhieu anh
    @OneToMany(mappedBy = "roomType")
    private List<RoomImage> roomImages;
}
