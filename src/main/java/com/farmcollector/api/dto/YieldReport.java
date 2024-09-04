package com.farmcollector.api.dto;

import lombok.Data;

@Data
public class YieldReport {
    private Integer expectedYield;
    private Integer actualYield;

    public YieldReport(int expected, int actual) {
        this.expectedYield = expected;
        this.actualYield = actual;
    }
}
