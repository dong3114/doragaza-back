package com.doragaza.driverlocation.handler.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
    private int status;
    private String message;
    private String path;
}

