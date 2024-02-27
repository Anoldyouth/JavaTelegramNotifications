package edu.java.bot.exception;

import edu.java.bot.dto.response.ApiErrorResponse;
import edu.java.bot.dto.response.ValidationError;
import edu.java.bot.dto.response.ValidationErrorsResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DefaultExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> validationException(MethodArgumentNotValidException exception) {
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

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiErrorResponse> globalPipeline(Throwable throwable) {
        var apiErrorResponse = new ApiErrorResponse(
                "Server Error",
                "500",
                throwable.getClass().getName(),
                throwable.getMessage(),
                throwable.getStackTrace()
        );

        return ResponseEntity.internalServerError().body(apiErrorResponse);
    }
}
