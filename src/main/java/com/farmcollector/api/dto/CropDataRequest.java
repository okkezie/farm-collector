package com.farmcollector.api.dto;

import lombok.Data;
import com.farmcollector.api.models.Crop;
import jakarta.validation.constraints.NotNull;

@Data
public class CropDataRequest {
    private Integer id;

    @NotNull(message = "Farmer name is required")
    private String farmerName;

    @NotNull(message = "Farm name is required")
    private String farmName;

    @NotNull(message = "Season is required")
    private String season;

    @NotNull(message = "Field is required")
    private String field;

    @NotNull(message = "Crop type is required")
    private String cropType;

    @NotNull(message = "Area in acres is required")
    private Integer areaInAcres;

    @NotNull(message = "Expected harvest is required")
    private Integer expectedHarvestIntons;

    private Integer actualHarvestInTons;

    public Crop convertToCropObject() {
        var crop = new Crop();
        crop.setAreaInAcres(areaInAcres);
        crop.setCropType(cropType);
        crop.setExpectedHarvestInTons(expectedHarvestIntons);
        crop.setFarmName(farmName);
        crop.setField(field);
        crop.setSeason(season);
        crop.setFarmerName(farmerName);
        
        return crop;
    }

}
