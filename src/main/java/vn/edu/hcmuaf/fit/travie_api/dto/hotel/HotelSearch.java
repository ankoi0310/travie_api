package vn.edu.hcmuaf.fit.travie_api.dto.hotel;

import lombok.Data;

@Data
public class HotelSearch {
    private String keyword;
    private int page = 1;
    private int size = 5;
}
