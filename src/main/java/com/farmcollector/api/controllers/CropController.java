package com.farmcollector.api.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farmcollector.api.dto.CropDataRequest;
import com.farmcollector.api.dto.ErrorResponse;
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
@RequestMapping(path = "/api/crops")
public class CropController {

    private final CropRepository cropRepsitory;
    
    @Operation(summary = "Planting API: Allows farmers to record planting data with expected yield.")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "Planting data successfully saved.",
            content = { @Content(mediaType = "application/json",
            schema = @Schema(implementation = com.farmcollector.api.dto.ApiResponse.class)) }),
        @ApiResponse(responseCode = "400", description = "Bad Request",
            content = { @Content(mediaType = "application/json",
            schema = @Schema(implementation = com.farmcollector.api.dto.ApiResponse.class)) }),
        @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = { @Content(mediaType = "application/json",
            schema = @Schema(implementation = com.farmcollector.api.dto.ApiResponse.class)) })
    })
    @PostMapping
    public ResponseEntity<com.farmcollector.api.dto.ApiResponse> createNewCrop(@Validated @RequestBody CropDataRequest request, BindingResult result) {
        log.info("Prorocessing request to create new crop record");
        try {
            var errors = validateRequestObject(result);
            if (errors != null) {
                return ResponseEntity.badRequest().body(com.farmcollector.api.dto.ApiResponse.returnFailedResponse(errors));
            }
            var crop = request.convertToCropObject();
            crop = cropRepsitory.save(crop);
            return ResponseEntity.ok().body(com.farmcollector.api.dto.ApiResponse.returnSuccessfulResponse(crop));
        }
        catch (Exception e) {
            log.error("Something went wrong while saving crop record.", e);
            var error = new ErrorResponse(e.getLocalizedMessage());
            return ResponseEntity.internalServerError().body(com.farmcollector.api.dto.ApiResponse.returnFailedResponse(error));
        }
    }

    @Operation(summary = "Harvesting API: Allows farmers to record actual harvested yield.")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "Harvesting data successfully saved.",
            content = { @Content(mediaType = "application/json",
            schema = @Schema(implementation = com.farmcollector.api.dto.ApiResponse.class)) }),
        @ApiResponse(responseCode = "400", description = "Bad Request",
            content = { @Content(mediaType = "application/json",
            schema = @Schema(implementation = com.farmcollector.api.dto.ApiResponse.class)) }),
        @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = { @Content(mediaType = "application/json",
            schema = @Schema(implementation = com.farmcollector.api.dto.ApiResponse.class)) })
    })
    @PatchMapping
    public ResponseEntity<com.farmcollector.api.dto.ApiResponse> updateCrop(@RequestBody CropDataRequest request) {
        log.info("Prorocessing request to update crop record");
        try {
            if (request == null || request.getId() == null) {
                throw new Exception("Original crop id is required.");
            }
            if (request.getActualHarvestInTons() == null) {
                throw new Exception("Actual harvest yield value is required.");
            }
            var crop = cropRepsitory.findById(request.getId())
                .orElseThrow(() -> new Exception("Crop record does not exist."));
            crop.setActualHarvestInTons(request.getActualHarvestInTons());
            crop = cropRepsitory.save(crop);
            return ResponseEntity.ok().body(com.farmcollector.api.dto.ApiResponse.returnSuccessfulResponse(crop));
        }
        catch (Exception e) {
            log.error("Something went wrong while saving crop record.", e);
            var error = new ErrorResponse(e.getLocalizedMessage());
            return ResponseEntity.badRequest().body(com.farmcollector.api.dto.ApiResponse.returnFailedResponse(error));
        }
    }

    private List<ErrorResponse> validateRequestObject(BindingResult result) {
        List<ErrorResponse> errors = null;
        if(result.hasErrors()) {
            errors = new ArrayList<ErrorResponse>();
			log.debug("Validation errors occured");
			for(FieldError error : result.getFieldErrors() ) {
                errors.add(new ErrorResponse(error.getField() + ": " + error.getDefaultMessage()));
            }
		}
        return errors;
    }
}
