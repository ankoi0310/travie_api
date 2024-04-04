package vn.edu.hcmuaf.fit.travie_api.core.shared.enums;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("Nam"),
    FEMALE("Nữ"),
    OTHER("Khác");

    private final String value;

    Gender(String value) {
        this.value = value;
    }
}
