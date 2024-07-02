package vn.edu.hcmuaf.fit.travie_api.dto.user;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
public class AppUserGoogle {
    private String sub;
    private String email;
    private boolean email_verified;
    private String name;
    private String given_name;
    private String family_name;
    private String picture;
    private String locale;
}
