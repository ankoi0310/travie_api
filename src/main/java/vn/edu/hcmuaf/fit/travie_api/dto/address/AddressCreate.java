package vn.edu.hcmuaf.fit.travie_api.dto.address;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressCreate {
    private String detail;
    private long wardId;
    private long districtId;
    private long provinceId;

    @NotBlank
    private String fullAddress;
}
