package cyclone.lunchvoting.web;

import cyclone.lunchvoting.dto.ErrorResponse;
import cyclone.lunchvoting.exception.NotFoundException;
import cyclone.lunchvoting.exception.VotingIsNotActiveException;
import cyclone.lunchvoting.exception.VotingIsNotFinishedException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class RestExceptionHandler {


    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY) // 422
    @ExceptionHandler({VotingIsNotActiveException.class, VotingIsNotFinishedException.class})
    public ErrorResponse handleVotingException(HttpServletRequest request, RuntimeException e) {
        return errorResponse(request, e, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND) // 404
    @ExceptionHandler({NotFoundException.class})
    public ErrorResponse handleNotFoundException(HttpServletRequest request, NotFoundException e) {
        return errorResponse(request, e, HttpStatus.NOT_FOUND);
    }

    // handled well out of the box
//    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR) // 500
//    @ExceptionHandler({Exception.class})
//    public ErrorResponse handleInternalServerError(HttpServletRequest request, Exception e) throws Exception {
//        return errorResponse(request, e, HttpStatus.INTERNAL_SERVER_ERROR);
//    }


    private ErrorResponse errorResponse(HttpServletRequest request, Exception e, HttpStatus httpStatus) {
        return new ErrorResponse(new Date(), httpStatus.value(), e.getClass().getSimpleName(), e.getMessage(), request.getRequestURI());
    }
}
