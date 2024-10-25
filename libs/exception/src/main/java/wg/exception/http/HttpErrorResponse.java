package wg.exception.http;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class HttpErrorResponse {
    private final ZonedDateTime timestamp;
    private final String path;
    private final HttpStatus httpStatus;
    private final String message;

    public HttpErrorResponse(HttpStatus httpStatus, String path, String message) {
        timestamp = ZonedDateTime.now();
        this.httpStatus = httpStatus;
        this.path = path;
        this.message = message;
    }
}
