package com.sai.greenbus.service;

import com.sai.greenbus.entity.RouteInfo;
import com.sai.greenbus.entity.StagePricing;
import com.sai.greenbus.model.BusRoute;
import com.sai.greenbus.repository.RouteInfoRepository;
import com.sai.greenbus.repository.StagePricingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by saipkri on 03/09/17.
 */
@Service
public class RouteInfoService {

    private final RouteInfoRepository routeInfoRepository;
    private final StagePricingRepository stagePricingRepository;

    @Autowired
    public RouteInfoService(final RouteInfoRepository routeInfoRepository, final StagePricingRepository stagePricingRepository) {
        this.routeInfoRepository = routeInfoRepository;
        this.stagePricingRepository = stagePricingRepository;
    }

    @Cacheable(cacheNames = "routeInfoCache", key = "#p0.concat('routeInfoCache').concat(#p1)")
    public List<BusRoute> findRoutes(final String origin, final String destination) {
        List<List<RouteInfo>> routesPerBus = routeInfoRepository.findRoutes(origin, destination);
        return routesPerBus.stream()
                .map(routeInfos -> new List[]{routeInfos, extractJourney(origin, destination, routeInfos)})
                .filter(lists -> !lists[1].isEmpty())
                .map(arr -> buildBusRoute(origin, destination, (List<RouteInfo>) arr[0], (List<RouteInfo>) arr[1]))
                .collect(Collectors.toList());
    }

    @Cacheable(cacheNames = "routeDetailsCache", key = "#p0.concat('routeDetailsCache')")
    public List<BusRoute> findRouteDetails(final String busNo) {
        List<List<RouteInfo>> routesPerBus = routeInfoRepository.findRoutes(busNo);
        return routesPerBus.stream()
                .map(routes -> buildBusRoute(routes.get(0).getOrigin(), routes.get(routes.size() - 1).getDestination(), routes, routes))
                .collect(Collectors.toList());
    }

    public Optional<BusRoute> findRoute(final String origin, final String destination, final int busId) {
        return findRoutes(origin.trim(), destination.trim()).stream()
                .filter(busRoute -> busRoute.getBusId() == busId)
                .findFirst();
    }

    @Cacheable(cacheNames = "routeValidationCache", key = "#p0.concat('routeInfoCache').concat(#p1).concat(#p2)")
    public boolean isValidBusForRoute(final String origin, final String destination, final Integer busId) {
        return routeInfoRepository.isValidBusForRoute(origin, destination, busId);
    }

    @CacheEvict(allEntries = true)
    public void clearCache() {
    }

    private BusRoute buildBusRoute(final String origin, final String destination, final List<RouteInfo> routeInfos, final List<RouteInfo> journey) {
        BusRoute busRoute = new BusRoute();
        busRoute.setBusId(routeInfos.get(0).getBusId());
        busRoute.setBusNo(routeInfos.get(0).getBusNo());
        busRoute.setBusType(routeInfos.get(0).getBusType());
        busRoute.setStartingPoint(routeInfos.get(0).getOrigin());
        busRoute.setEndingPoint(routeInfos.get(routeInfos.size() - 1).getDestination());
        busRoute.setOrigin(routeInfos.stream().filter(routeInfo -> routeInfo.getOrigin().equalsIgnoreCase(origin)).findFirst().get().getOrigin());
        busRoute.setDestination(routeInfos.stream().filter(routeInfo -> routeInfo.getDestination().equalsIgnoreCase(destination)).findFirst().get().getDestination());
        busRoute.setFullRoute(routeInfos);
        busRoute.setJourney(journey);
        busRoute.setTotalDistanceInKm(journey.stream().mapToDouble(RouteInfo::getDistanceInKm).reduce((a, b) -> a + b).getAsDouble());
        busRoute.setFrequencyInMinutes(journey.stream().filter(routeInfo -> routeInfo.getOrigin().equalsIgnoreCase(origin)).findFirst().get().getFrequencyinminutes());
        busRoute.setTotalTimeInMinutes(journey.stream().mapToDouble(RouteInfo::getMaxTravelTimeMinutes).reduce((a, b) -> a + b).getAsDouble());

        StagePricing pricing = stagePricingRepository.findStagePricing(busRoute.getBusType());
        double totalFare = pricing.getFarePerStage() * routeInfos.stream().mapToInt(RouteInfo::getStageSeqId).distinct().count();
        busRoute.setTotalFare(totalFare);
        return busRoute;
    }

    private List<RouteInfo> extractJourney(final String origin, final String destination, final List<RouteInfo> routes) {
        boolean start = false;
        boolean stop = false;
        List<RouteInfo> journey = new ArrayList<>();
        for (RouteInfo routeInfo : routes) {
            if (routeInfo.getOrigin().equalsIgnoreCase(origin.trim())) {
                start = true;
            }
            if (start && !stop) {
                journey.add(routeInfo);
            }
            if (routeInfo.getDestination().equalsIgnoreCase(destination.trim())) {
                stop = true;
            }
        }
        return journey;
    }


}
