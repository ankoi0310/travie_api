package vn.edu.hcmuaf.fit.travie_api.repository.impl;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.travie_api.core.repository.AbstractRepository;
import vn.edu.hcmuaf.fit.travie_api.entity.Banner;
import vn.edu.hcmuaf.fit.travie_api.entity.QBanner;
import vn.edu.hcmuaf.fit.travie_api.repository.BannerRepository;

import java.util.Optional;

@Repository
public class BannerRepositoryImpl extends AbstractRepository<Banner, Long> implements BannerRepository {
    private final QBanner qBanner = QBanner.banner;

    protected BannerRepositoryImpl(EntityManager entityManager) {
        super(Banner.class, entityManager);
    }

    @Override
    public Optional<Banner> findByTitle(String title) {
        return Optional.ofNullable(queryFactory.selectFrom(qBanner)
                                               .where(qBanner.title.eq(title))
                                               .fetchFirst());
    }
}
