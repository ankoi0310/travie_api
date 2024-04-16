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
@Table(name = "facility")
public class Facility extends BaseEntity {
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "facilities")
    private List<Room> rooms;

    private boolean deleted = false;
}
