package com.sai.greenbus.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by saipkri on 02/09/17.
 */
@Entity
public class StagePricing implements Serializable {

    @Id
    private Long id;

    @Column(name = "bustype")
    private String busType;

    @Column(name = "fareperstage")
    private Double farePerStage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public Double getFarePerStage() {
        return farePerStage;
    }

    public void setFarePerStage(Double farePerStage) {
        this.farePerStage = farePerStage;
    }
}
