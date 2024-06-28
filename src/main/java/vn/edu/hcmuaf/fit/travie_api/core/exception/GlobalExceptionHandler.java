package vn.edu.hcmuaf.fit.travie_api.core.exception;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.travie_api.core.handler.HttpResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<HttpResponse> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(HttpResponse.fail(ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<HttpResponse> handleRuntimeException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(HttpResponse.fail("Máy chủ đang gặp sự cố. Vui lòng thử lại sau."));
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ResponseEntity<HttpResponse> handleServiceUnavailableException(ServiceUnavailableException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(HttpResponse.fail(ex.getMessage()));
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<HttpResponse> handleForbiddenException(ForbiddenException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(HttpResponse.fail(ex.getMessage()));
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<HttpResponse> handleBindException(BindException ex) {
        StringBuilder message = new StringBuilder();
        ex.getFieldErrors().forEach(fieldError -> message.append(fieldError.getDefaultMessage()).append(". "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpResponse.fail(message.toString()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<HttpResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        StringBuilder message = new StringBuilder();
        ex.getBindingResult().getFieldErrors()
          .forEach(fieldError -> message.append(fieldError.getDefaultMessage()).append(". "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpResponse.fail(message.toString()));
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<HttpResponse> handleBadRequestException(BadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpResponse.fail(ex.getMessage()));
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<HttpResponse> handleJWTVerificationException(JwtException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HttpResponse.fail(ex.getMessage()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<HttpResponse> handleUnauthorizedException(UnauthorizedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HttpResponse.fail(ex.getMessage()));
    }

    @ExceptionHandler(UnsupportFileTypeException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ResponseEntity<HttpResponse> handleUnsupportFileTypeException(UnsupportFileTypeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpResponse.fail(ex.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<HttpResponse> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(HttpResponse.fail(ex.getMessage()));
    }

    @ExceptionHandler(TooManyRequestException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public ResponseEntity<HttpResponse> handleTooManyRequestException(TooManyRequestException ex) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(HttpResponse.fail(ex.getMessage()));
    }

    @ExceptionHandler(GoneException.class)
    @ResponseStatus(HttpStatus.GONE)
    public ResponseEntity<HttpResponse> handleGoneException(GoneException ex) {
        return ResponseEntity.status(HttpStatus.GONE).body(HttpResponse.fail(ex.getMessage()));
    }
}
