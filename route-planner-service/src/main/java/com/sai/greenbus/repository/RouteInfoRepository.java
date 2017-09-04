package com.sai.greenbus.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sai.greenbus.entity.QRouteInfo;
import com.sai.greenbus.entity.RouteInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by saipkri on 03/09/17.
 */
@Repository
public class RouteInfoRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public RouteInfoRepository(final JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }


    public List<List<RouteInfo>> findRoutes(final String origin, final String destination) {
        QRouteInfo qRouteInfo = QRouteInfo.routeInfo;

        List<Integer> busesPassingThroughOrigin = jpaQueryFactory
                .selectFrom(qRouteInfo)
                .select(qRouteInfo.busId)
                .where(qRouteInfo.origin.equalsIgnoreCase(origin))
                .distinct()
                .fetch();

        return busesPassingThroughOrigin.stream()
                .filter(busId -> jpaQueryFactory.selectFrom(qRouteInfo).where(qRouteInfo.destination.equalsIgnoreCase(destination).and(qRouteInfo.busId.eq(busId))).fetchCount() > 0)
                .map(busId -> jpaQueryFactory.selectFrom(qRouteInfo).where(qRouteInfo.busId.eq(busId)).orderBy(qRouteInfo.legSeqNo.asc()).fetch())
                .collect(Collectors.toList());
    }

    public List<List<RouteInfo>> findRoutes(final String busNo) {
        QRouteInfo qRouteInfo = QRouteInfo.routeInfo;

        List<Integer> buses = jpaQueryFactory
                .selectFrom(qRouteInfo)
                .select(qRouteInfo.busId)
                .where(qRouteInfo.busNo.equalsIgnoreCase(busNo.trim()))
                .distinct()
                .fetch();

        return buses.stream()
                .map(bus -> jpaQueryFactory.selectFrom(qRouteInfo).where(qRouteInfo.busId.eq(bus)).orderBy(qRouteInfo.legSeqNo.asc()).fetch())
                .collect(Collectors.toList());
    }


    public boolean isValidBusForRoute(final String origin, final String destination, final Integer busId) {
        QRouteInfo qRouteInfo = QRouteInfo.routeInfo;
        List<RouteInfo> routes = jpaQueryFactory.selectFrom(qRouteInfo)
                .where(qRouteInfo.busId.eq(busId))
                .fetch();

        return routes.stream()
                .filter(routeInfo -> routeInfo.getDestination().equalsIgnoreCase(destination.trim()) || routeInfo.getOrigin().equalsIgnoreCase(origin.trim()))
                .count() == 2;
    }
}
