package com.sai.greenbus.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sai.greenbus.entity.QStagePricing;
import com.sai.greenbus.entity.StagePricing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by saipkri on 03/09/17.
 */
@Repository
public class StagePricingRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public StagePricingRepository(final JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }


    public StagePricing findStagePricing(final String busType) {
        QStagePricing qStagePricing = QStagePricing.stagePricing;
        System.out.println(jpaQueryFactory.selectFrom(qStagePricing).fetchAll().fetch());
        return jpaQueryFactory
                .selectFrom(qStagePricing)
                .where(qStagePricing.busType.eq(busType))
                .fetchOne();
    }

    @CacheEvict(allEntries = true)
    public void clearCache() {
    }
}
