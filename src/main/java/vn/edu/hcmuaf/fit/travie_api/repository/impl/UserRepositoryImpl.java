package vn.edu.hcmuaf.fit.travie_api.repository.impl;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.travie_api.core.repository.AbstractRepository;
import vn.edu.hcmuaf.fit.travie_api.entity.AppUser;
import vn.edu.hcmuaf.fit.travie_api.entity.QAppUser;
import vn.edu.hcmuaf.fit.travie_api.repository.UserRepository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl extends AbstractRepository<AppUser, Long> implements UserRepository {
    private final QAppUser qAppUser = QAppUser.appUser;

    protected UserRepositoryImpl(EntityManager entityManager) {
        super(AppUser.class, entityManager);
    }

    @Override
    public Optional<AppUser> findByUsername(String username) {
        // username is email or phone
        return Optional.ofNullable(queryFactory
                .selectFrom(qAppUser)
                .where(qAppUser.nickname.eq(username).or(qAppUser.email.eq(username)).or(qAppUser.phone.eq(username)))
                .fetchOne());
    }

    @Override
    public Optional<AppUser> findByNickname(String nickname) {
        return Optional.ofNullable(queryFactory
                .selectFrom(qAppUser)
                .where(qAppUser.nickname.eq(nickname))
                .fetchOne());
    }

    @Override
    public Optional<AppUser> findByEmail(String email) {
        return Optional.ofNullable(queryFactory
                .selectFrom(qAppUser)
                .where(qAppUser.email.eq(email))
                .fetchOne());
    }

    @Override
    public Optional<AppUser> findByPhone(String phone) {
        return Optional.ofNullable(queryFactory
                .selectFrom(qAppUser)
                .where(qAppUser.phone.eq(phone))
                .fetchOne());
    }

    @Override
    public Optional<AppUser> findByFacebookId(String facebookId) {
        return Optional.empty();
    }

    @Override
    public Optional<AppUser> findByGoogleId(String googleId) {
        return Optional.empty();
    }
}
