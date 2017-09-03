package com.sai.greenbus.model;

import com.sai.greenbus.entity.RouteInfo;
import org.springframework.hateoas.ResourceSupport;

import java.io.Serializable;
import java.util.List;

/**
 * Created by saipkri on 03/09/17.
 */
public class BusRoute extends ResourceSupport implements Serializable {

    private Integer busId;

    private String busNo;
    private String busType;

    private String origin;
    private String destination;

    private String startingPoint;
    private String endingPoint;

    private double totalDistanceInKm;
    private double totalTimeInMinutes;
    private double totalFare;

    private List<RouteInfo> routes;

    public Integer getBusId() {
        return busId;
    }

    public void setBusId(Integer busId) {
        this.busId = busId;
    }

    public String getBusNo() {
        return busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public String getEndingPoint() {
        return endingPoint;
    }

    public void setEndingPoint(String endingPoint) {
        this.endingPoint = endingPoint;
    }

    public double getTotalDistanceInKm() {
        return totalDistanceInKm;
    }

    public void setTotalDistanceInKm(double totalDistanceInKm) {
        this.totalDistanceInKm = totalDistanceInKm;
    }

    public double getTotalTimeInMinutes() {
        return totalTimeInMinutes;
    }

    public void setTotalTimeInMinutes(double totalTimeInMinutes) {
        this.totalTimeInMinutes = totalTimeInMinutes;
    }

    public double getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(double totalFare) {
        this.totalFare = totalFare;
    }

    public List<RouteInfo> getRoutes() {
        return routes;
    }

    public void setRoutes(List<RouteInfo> routes) {
        this.routes = routes;
    }
}
