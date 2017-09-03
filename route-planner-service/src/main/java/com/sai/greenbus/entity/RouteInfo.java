package com.sai.greenbus.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by saipkri on 02/09/17.
 */
@Entity
public class RouteInfo implements Serializable {

    @Id
    private Long id;

    @Column(name = "busid")
    private Integer busId;

    @Column(name = "busno")
    private String busNo;

    @Column
    private String origin;

    @Column
    private String destination;

    @Column
    private Integer timeatorigin;

    @Column(name = "legseqno")
    private Integer legSeqNo;

    @Column(name = "maxtraveltimeminutes")
    private Integer maxTravelTimeMinutes;

    @Column(name = "distanceinkm")
    private Double distanceInKm;

    @Column(name = "stageseqid")
    private Integer stageSeqId;

    @Column(name = "bustype")
    private String busType;

    @Column
    private boolean disabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getTimeatorigin() {
        return timeatorigin;
    }

    public void setTimeatorigin(Integer timeatorigin) {
        this.timeatorigin = timeatorigin;
    }

    public Integer getLegSeqNo() {
        return legSeqNo;
    }

    public void setLegSeqNo(Integer legSeqNo) {
        this.legSeqNo = legSeqNo;
    }

    public Integer getMaxTravelTimeMinutes() {
        return maxTravelTimeMinutes;
    }

    public void setMaxTravelTimeMinutes(Integer maxTravelTimeMinutes) {
        this.maxTravelTimeMinutes = maxTravelTimeMinutes;
    }

    public Double getDistanceInKm() {
        return distanceInKm;
    }

    public void setDistanceInKm(Double distanceInKm) {
        this.distanceInKm = distanceInKm;
    }

    public Integer getStageSeqId() {
        return stageSeqId;
    }

    public void setStageSeqId(Integer stageSeqId) {
        this.stageSeqId = stageSeqId;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
