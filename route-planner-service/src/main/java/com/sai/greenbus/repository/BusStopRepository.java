package com.sai.greenbus.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sai.greenbus.entity.BusStop;
import com.sai.greenbus.entity.QBusStop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by saipkri on 03/09/17.
 */
@Repository
public class BusStopRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public BusStopRepository(final JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }


    public List<BusStop> findBusStops(final String name) {
        QBusStop qBusStop = QBusStop.busStop;

        return jpaQueryFactory
                .selectFrom(qBusStop)
                .where(qBusStop.name.equalsIgnoreCase(name.trim()))
                .fetch();
    }
}
