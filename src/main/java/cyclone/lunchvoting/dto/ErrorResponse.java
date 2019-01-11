package cyclone.lunchvoting.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * see {@link org.springframework.boot.web.servlet.error.DefaultErrorAttributes}
 */
@Data
@NoArgsConstructor
public class ErrorResponse {
    private Date timestamp;
    private int status;
    private String error, message, path;
//    private String[] details;



    public ErrorResponse(Date timestamp, int status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}
