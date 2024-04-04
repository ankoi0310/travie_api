package vn.edu.hcmuaf.fit.travie_api.dto.address;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressCreate {
    private String detail;
    private int wardId;
    private int districtId;
    private int provinceId;

    @NotBlank
    private String fullAddress;
}
