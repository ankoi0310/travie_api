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
@Table(name = "payment_type")
public class PaymentMethod extends BaseEntity {
    private String name;
    private String description;
    private boolean isOnline;
    private boolean activated;
}
