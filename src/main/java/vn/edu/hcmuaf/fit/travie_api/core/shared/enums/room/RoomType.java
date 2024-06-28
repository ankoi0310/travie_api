package vn.edu.hcmuaf.fit.travie_api.core.shared.enums.room;

import lombok.Getter;

@Getter
public enum RoomType {
    SGL("Single Bedroom", "1 giường đơn"),
    TWN("Twin Bedroom", "2 giường đơn"),
    DBL("Double Bedroom", "1 giường đôi"),
    TRPL_1("Triple Bedroom", "3 giường đơn"),
    TRPL_2("Triple Bedroom", "1 giường đôi và 1 giường đơn");

    private final String en;
    private final String vi;

    RoomType(String en, String vi) {
        this.en = en;
        this.vi = vi;
    }
}
