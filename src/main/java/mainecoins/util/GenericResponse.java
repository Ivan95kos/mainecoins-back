package mainecoins.util;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class GenericResponse {

    private String message;

    private HttpStatus httpStatus;

    private int httpCode;

    public GenericResponse(final String message) {
        super();
        this.message = message;
    }

    public GenericResponse(final String message, final HttpStatus httpStatus, int httpCode) {
        super();
        this.httpStatus = httpStatus;
        this.message = message;
        this.httpCode = httpCode;
    }
}
