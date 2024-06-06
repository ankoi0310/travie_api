package vn.edu.hcmuaf.fit.travie_api.core.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class HttpResponse {
    private boolean success;
    private int statusCode;
    private String message;
    private Object data;

    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private final LocalDateTime timestamp = LocalDateTime.now();

    public static HttpResponse success(String message) {
        return builder().success(true).message(message).build();
    }

    public static HttpResponse success(Object data, String message) {
        return builder().success(true).data(data).message(message).build();
    }

    public static HttpResponse success(int statusCode, String message) {
        return builder().success(true).statusCode(statusCode).message(message).build();
    }

    public static HttpResponse success(int statusCode, Object data, String message) {
        return builder().success(true).statusCode(statusCode).data(data).message(message).build();
    }

    public static HttpResponse fail(String message) {
        return builder().success(false).message(message).build();
    }

    public static HttpResponse fail(int statusCode, String message) {
        return builder().success(false).statusCode(statusCode).message(message).build();
    }
}
