package vn.edu.hcmuaf.fit.travie_api.repository.impl;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.travie_api.core.repository.AbstractRepository;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.user.UserRole;
import vn.edu.hcmuaf.fit.travie_api.entity.AppRole;
import vn.edu.hcmuaf.fit.travie_api.entity.QAppRole;
import vn.edu.hcmuaf.fit.travie_api.repository.AppRoleRepository;

import java.util.Optional;

@Repository
public class AppRoleRepositoryImpl extends AbstractRepository<AppRole, Long> implements AppRoleRepository {
    private final QAppRole qEntity = QAppRole.appRole;

    protected AppRoleRepositoryImpl(EntityManager entityManager) {
        super(AppRole.class, entityManager);
    }

    @Override
    public Optional<AppRole> findByName(String name) {
        return Optional.ofNullable(queryFactory
                .selectFrom(qEntity)
                .where(qEntity.name.eq(name))
                .fetchOne()
        );
    }

    @Override
    public Optional<AppRole> findByRole(UserRole role) {
        return Optional.ofNullable(queryFactory
                .selectFrom(qEntity)
                .where(qEntity.name.eq(role.name()))
                .fetchOne()
        );
    }
}
