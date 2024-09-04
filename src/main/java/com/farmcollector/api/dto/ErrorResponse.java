package com.farmcollector.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorResponse {
    private String reason;
    private int code;

    public ErrorResponse(String message) {
        this.reason = message;
        this.code = 500;
    }
}
