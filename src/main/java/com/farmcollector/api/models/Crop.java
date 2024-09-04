package com.farmcollector.api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Crop {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String farmerName;
    private String farmName;
    private String season;
    private String field;
    private String cropType;
    private Integer areaInAcres;
    private Integer expectedHarvestInTons;
    private Integer actualHarvestInTons;
}
