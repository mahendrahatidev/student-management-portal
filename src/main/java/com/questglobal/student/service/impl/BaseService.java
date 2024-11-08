package com.questglobal.student.service.impl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.questglobal.student.response.ErrorResponse;

public class BaseService {

    protected <T> ResponseEntity<T> createSuccessResponse(T data) {
        return ResponseEntity.ok().body(data);
    }

    protected ResponseEntity<ErrorResponse> createErrorResponse(HttpStatus httpStatus,String errorMessage, String errorCode) {
        ErrorResponse error = new ErrorResponse(errorMessage, errorCode);
        return ResponseEntity.status(httpStatus).body(error);
    }
}
