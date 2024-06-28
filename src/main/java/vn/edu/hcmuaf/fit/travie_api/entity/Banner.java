package vn.edu.hcmuaf.fit.travie_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.travie_api.core.entity.BaseEntity;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "banner")
public class Banner extends BaseEntity {
    private String title;
    private String description;
    private String image;
    private String url;
}
