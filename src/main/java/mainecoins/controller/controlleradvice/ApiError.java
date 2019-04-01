package mainecoins.controller.controlleradvice;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@Data
public class ApiError {

    private HttpStatus status;
    private int httpCode;
    private List<String> message;

    public ApiError(HttpStatus status, int httpCode, List<String> message) {
        super();
        this.status = status;
        this.httpCode = httpCode;
        this.message = message;
    }

    public ApiError(HttpStatus status, int httpCode, String message) {
        super();
        this.status = status;
        this.httpCode = httpCode;
        this.message = Arrays.asList(message);
    }
}
