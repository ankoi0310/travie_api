package vn.edu.hcmuaf.fit.travie_api.dto.address;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressCreate {
    private String detail;
    private Long wardId;
    private Long districtId;
    private Long provinceId;

    @NotBlank
    private String fullAddress;
}
