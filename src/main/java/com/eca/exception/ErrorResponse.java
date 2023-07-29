package com.eca.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

    private String code;
    private String message;
    private int status;
    private String category;
}
