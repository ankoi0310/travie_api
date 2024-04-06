package vn.edu.hcmuaf.fit.travie_api.core.shared.enums;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("Nam", "male"),
    FEMALE("Nữ", "female"),
    OTHER("Khác", "other");

    private final String name;
    private final String value;

    Gender(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
