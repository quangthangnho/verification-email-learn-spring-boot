package com.verificationemail.verificationemail.constant;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private LocalDateTime timestamp;
    private int status;
    private boolean success;
    private T data;
    private String error;
    private String message;
    private String exceptionMessage;

    public ApiResponse(HttpStatus status) {
        this.status = status.value();
        this.success = status.is2xxSuccessful();
        if (!this.success) {
            this.timestamp = LocalDateTime.now();
            this.error = status.getReasonPhrase();
        }
    }

    public ApiResponse(T data, HttpStatus status) {
        this(status);
        this.data = data;
    }

    public ApiResponse(HttpStatus status, Throwable ex) {
        this(status);
        this.exceptionMessage = ex.getLocalizedMessage();
    }

    public ApiResponse(HttpStatus status, String message, Throwable ex) {
        this(status, ex);
        this.message = message;
    }

    public static <T> ApiResponse<T> ok() {
        return new ApiResponse<>(HttpStatus.OK);
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(data, HttpStatus.OK);
    }

    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>(data, HttpStatus.CREATED);
    }

    public static <T> ApiResponse<T> internalServerError(Throwable ex) {
        return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    public ResponseEntity<T> build() {
        return new ResponseEntity(this, HttpStatus.valueOf(this.status));
    }


}
