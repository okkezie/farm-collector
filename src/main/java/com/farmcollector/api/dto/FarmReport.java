package com.farmcollector.api.dto;

import lombok.Data;

@Data
public class FarmReport {
    private String farmName;
    private String season;
    private YieldReport yield;

    public FarmReport(int expectedHarvest, int actualHarvest, String farmName, String season) {
        this.yield = new YieldReport(expectedHarvest, actualHarvest);
        this.farmName = farmName;
        this.season = season;
    }
}