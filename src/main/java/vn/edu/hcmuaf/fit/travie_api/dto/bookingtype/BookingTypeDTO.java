package vn.edu.hcmuaf.fit.travie_api.dto.bookingtype;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.travie_api.core.dto.BaseDTO;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.TimeUnit;

import java.sql.Time;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BookingTypeDTO extends BaseDTO {
    private String name;
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private Time checkInTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private Time checkOutTime;

    private boolean isHourly;
    private boolean isByDay;
    private boolean isOvernight;

    private TimeUnit unit;
}
