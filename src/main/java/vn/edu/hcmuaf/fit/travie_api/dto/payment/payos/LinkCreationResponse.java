package vn.edu.hcmuaf.fit.travie_api.dto.payment.payos;

import lombok.Data;

@Data
public class LinkCreationResponse {
    private String bin;
    private String accountNumber;
    private String accountName;
    private int amount;
    private String description;
    private int orderCode;
    private String currency;
    private String paymentLinkId;
    private String status;
    private String checkoutUrl;
    private String qrCode;
}
