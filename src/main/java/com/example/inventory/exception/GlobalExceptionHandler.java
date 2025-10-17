package com.example.inventory.exception;

import com.example.inventory.dto.response.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException ex) {

        Map<String, String> error = ex.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : "Invalid value"
                ));

        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .message("Validation failed")
                .error(error)
                .build();

        return ResponseEntity.badRequest().body(errorResponseDto);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        Map<String, String> errors = Map.of(
                ex.getName(), "Invalid value: " + ex.getValue()
        );

        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .message("Validation failed")
                .error(errors)
                .build();

        return ResponseEntity.badRequest().body(errorResponseDto);
    }

    @ExceptionHandler({ItemNotFoundException.class, SupplyNotFoundException.class, InventoryNotFoundException.class})
    public ResponseEntity<ErrorResponseDto> handleNotFound(RuntimeException ex) {

        ErrorResponseDto error = ErrorResponseDto.builder()
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponseDto> handleInsufficientStock(InsufficientStockException ex) {

        ErrorResponseDto error = ErrorResponseDto.builder()
                .message(ex.getMessage())
                .build();

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(ReservationCancellationException.class)
    public ResponseEntity<ErrorResponseDto> handleReservationCancellation(ReservationCancellationException ex) {

        ErrorResponseDto error = ErrorResponseDto.builder()
                .message(ex.getMessage())
                .build();

        return ResponseEntity.badRequest().body(error); // 400 Bad Request
    }
}
