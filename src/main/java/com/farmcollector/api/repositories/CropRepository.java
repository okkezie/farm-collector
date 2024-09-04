package com.farmcollector.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.farmcollector.api.dto.CropReport;
import com.farmcollector.api.dto.FarmReport;
import com.farmcollector.api.models.Crop;

@Transactional
public interface CropRepository extends CrudRepository<Crop, Integer>, PagingAndSortingRepository<Crop, Integer> {
    @Query("""
        SELECT
            new com.farmcollector.api.dto.CropReport(
                SUM(c.expectedHarvestInTons),
                SUM(c.actualHarvestInTons),
                c.cropType,
                c.season)
            FROM
                Crop c
            WHERE c.season = :season
            GROUP BY c.cropType
    """)
    List<CropReport> getCropSummaryReport(String season);

    @Query("""
        SELECT
            new com.farmcollector.api.dto.FarmReport(
                SUM(c.expectedHarvestInTons),
                SUM(c.actualHarvestInTons),
                c.farmName,
                c.season)
            FROM
                Crop c
            WHERE c.season = :season
            GROUP BY c.farmName
    """)
    List<FarmReport> getFarmSummaryReport(String season);
}
