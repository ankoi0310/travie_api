package vn.edu.hcmuaf.fit.travie_api.core.shared.enums.otp;

import jakarta.persistence.AttributeConverter;

public enum OTPState {
    ACTIVE,
    VERIFIED,
    EXPIRED;

    public static class Converter implements AttributeConverter<OTPState, String> {
        @Override
        public String convertToDatabaseColumn(OTPState attribute) {
            return attribute.name().toLowerCase();
        }

        @Override
        public OTPState convertToEntityAttribute(String dbData) {
            return OTPState.valueOf(dbData.toUpperCase());
        }
    }
}
