package edu.java.exception;

import edu.java.dto.response.exception.ApiErrorResponse;
import edu.java.dto.response.exception.ValidationError;
import edu.java.dto.response.exception.ValidationErrorsResponse;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DefaultExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> reactiveApiException(MethodArgumentNotValidException exception) {
        List<ValidationError> validationErrors = exception.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> {
                    if (error instanceof FieldError fieldError) {
                        return new ValidationError(fieldError.getField(), fieldError.getDefaultMessage());
                    } else {
                        return new ValidationError(error.getObjectName(), error.getDefaultMessage());
                    }
                })
                .toList();

        var response = new ValidationErrorsResponse(
                "Validation Error",
                "400",
                validationErrors
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> notFoundException(NotFoundException exception) {
        var response = new ApiErrorResponse(
                "Not Found Exception",
                "404",
                exception.getClass().getName(),
                exception.getMessage(),
                exception.getStackTrace()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> globalPipeline(Exception exception) {
        var apiErrorResponse = new ApiErrorResponse(
                "Server Error",
                "500",
                exception.getClass().getName(),
                exception.getMessage(),
                exception.getStackTrace()
        );

        return ResponseEntity.internalServerError().body(apiErrorResponse);
    }
}
