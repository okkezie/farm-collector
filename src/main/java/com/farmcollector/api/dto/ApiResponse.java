package com.farmcollector.api.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.farmcollector.api.models.Crop;

import lombok.Data;

@Data
public class ApiResponse {
    private static final String SUCCESSFUL = "Successful";
    private static final String FAILED = "Failed";

    private Collection<?> results;
    private Crop result;
    private String status;
    private int code;
    private List<ErrorResponse> errors;

    public static ApiResponse returnSuccessfulResponse(Crop body) {
        var result = new ApiResponse();
        result.setResult(body);
        result.setStatus(SUCCESSFUL);
        result.setCode(0);
        return result;
    }

    public static ApiResponse returnSuccessfulResponse(Collection<?> body) {
        var result = new ApiResponse();
        result.setResults(body);
        result.setStatus(SUCCESSFUL);
        result.setCode(0);
        return result;
    }

    public static ApiResponse returnFailedResponse(ErrorResponse error) {
        var result = new ApiResponse();
        var errors = new ArrayList<ErrorResponse>();
        errors.add(error);
        result.setErrors(errors);
        result.setStatus(FAILED);
        result.setCode(1);
        return result;
    }

    public static ApiResponse returnFailedResponse(List<ErrorResponse> errors) {
        var result = new ApiResponse();
        result.setErrors(errors);
        result.setStatus(FAILED);
        result.setCode(1);
        return result;
    }
}
