package com.farmcollector.api.dto;

import lombok.Data;

@Data
public class CropReport {
    private String cropType;
    private String season;
    private YieldReport yield;

    public CropReport(int expectedHarvest, int actualHarvest, String cropType, String season) {
        this.yield = new YieldReport(expectedHarvest, actualHarvest);
        this.cropType = cropType;
        this.season = season;
    }
}
