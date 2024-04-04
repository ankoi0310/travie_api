package vn.edu.hcmuaf.fit.travie_api.repository.approle;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.travie_api.core.repository.CustomRepository;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.UserRole;
import vn.edu.hcmuaf.fit.travie_api.entity.AppRole;

import java.util.Optional;

@NoRepositoryBean
public interface AppRoleRepository extends CustomRepository<AppRole, Long> {
    Optional<AppRole> findByName(String name);

    Optional<AppRole> findByRole(UserRole role);
}
