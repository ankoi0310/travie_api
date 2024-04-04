package vn.edu.hcmuaf.fit.travie_api.core.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public abstract class AbstractRepository<T, Long> extends SimpleJpaRepository<T, Long> {
    protected EntityManager entityManager;
    protected Class<T> clazz;
    protected JPAQueryFactory queryFactory;

    protected AbstractRepository(final Class<T> clazz, final EntityManager entityManager) {
        super(clazz, entityManager);
        this.clazz = clazz;
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    private AbstractRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }
}
