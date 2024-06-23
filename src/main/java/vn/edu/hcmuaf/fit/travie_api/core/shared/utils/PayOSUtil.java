package vn.edu.hcmuaf.fit.travie_api.core.shared.utils;

import org.apache.commons.codec.digest.HmacUtils;

public class PayOSUtil {
    public static String generateSignature(int amount, String cancelUrl, String description, int orderCode,
                                           String returnUrl, String checksumKey) {
        String signature = "amount=" + amount + "&cancelUrl=" + cancelUrl + "&description=" + description +
                "&orderCode=" + orderCode + "&returnUrl=" + returnUrl;

        // hash signature with HMACSHA256
        signature = new HmacUtils("HmacSHA256", checksumKey).hmacHex(signature);

        return signature;
    }
}
