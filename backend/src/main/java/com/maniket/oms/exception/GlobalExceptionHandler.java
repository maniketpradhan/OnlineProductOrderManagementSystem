package com.maniket.oms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.maniket.oms.dto.ApiResponse;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.MethodArgumentNotValidException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFound(ResourceNotFoundException ex) {

        ApiResponse<?> response =
                new ApiResponse<>(false, ex.getMessage(), null);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception ex) {

        ApiResponse<?> response =
                new ApiResponse<>(false, ex.getMessage(), null);

        return new ResponseEntity<>(response,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<Map<String,String>> handleValidation(MethodArgumentNotValidException ex){

    Map<String,String> errors = new HashMap<>();

    ex.getBindingResult().getFieldErrors().forEach(error ->

            errors.put(error.getField(), error.getDefaultMessage())

    );

    return ResponseEntity.badRequest().body(errors);

}
}