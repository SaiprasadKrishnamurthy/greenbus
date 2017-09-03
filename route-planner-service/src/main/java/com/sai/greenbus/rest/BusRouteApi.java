package com.sai.greenbus.rest;

import com.google.common.collect.ImmutableMap;
import com.sai.greenbus.model.BusRoute;
import com.sai.greenbus.service.RouteInfoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by saipkri on 03/09/17.
 */
@Api("Bus route API")
@RestController("api")
public class BusRouteApi {

    private final RouteInfoService routeInfoService;

    @Autowired
    public BusRouteApi(final RouteInfoService routeInfoService) {
        this.routeInfoService = routeInfoService;
    }

    @GetMapping("busroutes/{origin}/{destination}")
    public ResponseEntity<Resources<BusRoute>> busRoutes(final @PathVariable("origin") String origin, final @PathVariable("destination") String destination) {
        List<BusRoute> busroutes = routeInfoService.findRoutes(origin.trim(), destination.trim());
        // build the HAL links per bus route.
        buildLinks(origin, destination, busroutes);
        Resources<BusRoute> resource = new Resources<>(busroutes, linkTo(methodOn(BusRouteApi.class).busRoutes(origin.trim(), destination.trim())).withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    private void buildLinks(final String origin, final String destination, final List<BusRoute> busroutes) {
        busroutes.forEach(busRoute -> {
            busRoute.add(linkTo(methodOn(BusRouteApi.class).busRoute(origin.trim(), destination.trim(), busRoute.getBusId())).withSelfRel());
            busRoute.add(linkTo(methodOn(BusRouteApi.class).busRouteValidity(origin.trim(), destination.trim(), busRoute.getBusId())).withRel("validBusForRoute"));
            busRoute.add(linkTo(methodOn(BusRouteApi.class).busFrequencyInMinutes(origin.trim(), destination.trim(), busRoute.getBusNo())).withRel("busFrequencyInMinutes"));
        });
    }

    @GetMapping("busroute/{origin}/{destination}/{busId}")
    public ResponseEntity<Resource<BusRoute>> busRoute(final @PathVariable("origin") String origin, final @PathVariable("destination") String destination, final @PathVariable("busId") int busId) {
        Optional<BusRoute> busroute = routeInfoService.findRoute(origin.trim(), destination.trim(), busId);
        if (busroute.isPresent()) {
            Resource<BusRoute> resource = new Resource<>(busroute.get());
            resource.add(linkTo(methodOn(BusRouteApi.class).busRoute(origin.trim(), destination.trim(), busId)).withSelfRel());
            return new ResponseEntity<>(resource, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("busroutevalidity/{origin}/{destination}/{busId}")
    public ResponseEntity<Resource<ImmutableMap<String, Boolean>>> busRouteValidity(final @PathVariable("origin") String origin, final @PathVariable("destination") String destination, final @PathVariable("busId") int busId) {
        boolean isValid = routeInfoService.isValidBusForRoute(origin.trim(), destination.trim(), busId);
        return new ResponseEntity<>(new Resource<>(ImmutableMap.of("valid", isValid)), HttpStatus.OK);
    }

    @GetMapping("busfrequencyinminutes/{origin}/{destination}/{busNo}")
    public ResponseEntity<Resource<ImmutableMap<String, Double>>> busFrequencyInMinutes(final @PathVariable("origin") String origin, final @PathVariable("destination") String destination, final @PathVariable("busNo") String busNo) {
        double frequencyInMinutes = routeInfoService.busFrequencyInMinutes(origin.trim(), destination.trim(), busNo);
        return new ResponseEntity<>(new Resource<>(ImmutableMap.of("frequencyInMinutes", frequencyInMinutes)), HttpStatus.OK);
    }
}
