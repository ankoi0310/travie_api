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
@Table(name = "address")
public class Address extends BaseEntity {
    private String detail;
    private int wardId;
    private int districtId;
    private int provinceId;
    private String fullAddress;

//    @OneToOne(mappedBy = "address")
//    @PrimaryKeyJoinColumn
//    private Hotel hotel;
}
