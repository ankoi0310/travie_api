package vn.edu.hcmuaf.fit.travie_api.repository;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.travie_api.core.repository.CustomRepository;
import vn.edu.hcmuaf.fit.travie_api.entity.AppUser;

import java.util.Optional;

@NoRepositoryBean
public interface UserRepository extends CustomRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);

    Optional<AppUser> findByNickname(String nickname);

    Optional<AppUser> findByEmail(String email);

    Optional<AppUser> findByPhone(String phone);

    Optional<AppUser> findByFacebookId(String facebookId);

    Optional<AppUser> findByGoogleId(String googleId);
}
