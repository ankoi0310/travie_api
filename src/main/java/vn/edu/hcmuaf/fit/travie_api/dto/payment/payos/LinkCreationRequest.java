package vn.edu.hcmuaf.fit.travie_api.dto.payment.payos;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkCreationRequest {
    private int orderCode;
    private int amount;
    private String description;
    private String buyerName;
    private String buyerEmail;
    private String buyerPhone;
    private String buyerAddress;
    private List<LinkCreationItem> items;
    private String cancelUrl;
    private String returnUrl;
    private int expiredAt;
    private String signature;
}
