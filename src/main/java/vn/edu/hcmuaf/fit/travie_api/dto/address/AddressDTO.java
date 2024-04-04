package vn.edu.hcmuaf.fit.travie_api.dto.address;

import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.travie_api.core.dto.BaseDTO;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AddressDTO extends BaseDTO {
    private String detail;
    private int wardId;
    private int districtId;
    private int provinceId;
    private String fullAddress;
}
