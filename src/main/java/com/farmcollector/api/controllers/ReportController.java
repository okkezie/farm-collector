package com.farmcollector.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farmcollector.api.repositories.CropRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/reports")
public class ReportController {
    
    private final CropRepository cropRepository;

    @Operation(summary = "Get a report of yields for a season grouped by farms.")
    @ApiResponses(value = { 
    @ApiResponse(responseCode = "200", description = "Request successful", 
        content = { @Content(mediaType = "application/json", 
        schema = @Schema(implementation = com.farmcollector.api.dto.ApiResponse.class)) }),
    @ApiResponse(responseCode = "500", description = "Internal Server Error", 
        content = { @Content(mediaType = "application/json", 
        schema = @Schema(implementation = com.farmcollector.api.dto.ApiResponse.class)) }) })
    @GetMapping(path = "/{season}/farm")
    public ResponseEntity<?> getReportForFarm(@PathVariable("season") String season) {
        log.info("processing request to get report for farms per season: {}", season);
        var crops = cropRepository.getFarmSummaryReport(season);
        return ResponseEntity.ok().body(com.farmcollector.api.dto.ApiResponse.returnSuccessfulResponse(crops));
    }

    @Operation(summary = "Get a report of yields for a season grouped by crops.")
    @ApiResponses(value = { 
    @ApiResponse(responseCode = "200", description = "Request successful", 
        content = { @Content(mediaType = "application/json", 
        schema = @Schema(implementation = com.farmcollector.api.dto.ApiResponse.class)) }),
    @ApiResponse(responseCode = "500", description = "Internal Server Error", 
        content = { @Content(mediaType = "application/json", 
        schema = @Schema(implementation = com.farmcollector.api.dto.ApiResponse.class)) }) })
    @GetMapping(path = "/{season}/crop")
    public ResponseEntity<?> getReportForCrop(@PathVariable("season") String season) {
        log.info("processing request to get report for farms per season: {}", season);
        var crops = cropRepository.getFarmSummaryReport(season);
        return ResponseEntity.ok().body(com.farmcollector.api.dto.ApiResponse.returnSuccessfulResponse(crops));
    }
}
