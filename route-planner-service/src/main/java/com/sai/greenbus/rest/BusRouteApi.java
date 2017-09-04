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
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("busroutes")
    public ResponseEntity<Resources<BusRoute>> busRoutes(final @RequestParam("origin") String origin, final @RequestParam("destination") String destination) {
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
            busRoute.add(linkTo(methodOn(BusRouteApi.class).busRoute(busRoute.getBusNo().trim())).withRel("busRouteDetails"));
        });
    }

    private void buildLinks(final String busNo, final List<BusRoute> busroutes) {
        busroutes.forEach(busRoute -> {
            busRoute.add(linkTo(methodOn(BusRouteApi.class).busRoute(busNo.trim())).withSelfRel());
        });
    }

    @GetMapping("busroute/{busId}")
    public ResponseEntity<Resource<BusRoute>> busRoute(final @RequestParam("origin") String origin, final @RequestParam("destination") String destination, final @PathVariable("busId") int busId) {
        Optional<BusRoute> busroute = routeInfoService.findRoute(origin.trim(), destination.trim(), busId);
        if (busroute.isPresent()) {
            Resource<BusRoute> resource = new Resource<>(busroute.get());
            resource.add(linkTo(methodOn(BusRouteApi.class).busRoute(origin.trim(), destination.trim(), busId)).withSelfRel());
            return new ResponseEntity<>(resource, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("busroute/busNo/{busNo}")
    public ResponseEntity<Resources<BusRoute>> busRoute(final @PathVariable("busNo") String busName) {
        List<BusRoute> busroute = routeInfoService.findRouteDetails(busName.trim());
        if (!busroute.isEmpty()) {
            // build the HAL links per bus route.
            buildLinks(busName, busroute);
            Resources<BusRoute> resource = new Resources<>(busroute, linkTo(methodOn(BusRouteApi.class).busRoute(busName.trim())).withSelfRel());
            return new ResponseEntity<>(resource, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("busroutevalidity/{busId}")
    public ResponseEntity<Resource<ImmutableMap<String, Boolean>>> busRouteValidity(final @RequestParam("origin") String origin, final @RequestParam("destination") String destination, final @PathVariable("busId") int busId) {
        boolean isValid = routeInfoService.isValidBusForRoute(origin.trim(), destination.trim(), busId);
        return new ResponseEntity<>(new Resource<>(ImmutableMap.of("valid", isValid)), HttpStatus.OK);
    }

}
