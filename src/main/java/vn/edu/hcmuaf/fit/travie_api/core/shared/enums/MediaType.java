package vn.edu.hcmuaf.fit.travie_api.core.shared.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MediaType {
    IMAGE("image", "images"),
    VIDEO("video", "videos");

    private final String value;
    private final String path;
}
