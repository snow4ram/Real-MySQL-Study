package mysqlpoint.realmysqlpoint.exception;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.UnexpectedTypeException;
import mysqlpoint.realmysqlpoint.common.response.ErrorResponse;
import mysqlpoint.realmysqlpoint.common.response.HttpResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;

import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.login.AccountExpiredException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Custom Exception Handler
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handlerBusinessException(BusinessException e, HttpServletRequest request) {
        return ResponseEntity
                .status(e.getStatus())
                .body(ErrorResponse.of(
                        e.getMessage(),
                        request
                ));
    }

    /**
     * Validation 관련 Exception Handler
     */
    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            UnexpectedTypeException.class
    })
    public ResponseEntity<ErrorResponse> handleBindException(MethodArgumentNotValidException e, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpResponse.Fail.INVALID_INPUT_VALUE.getStatus())
                .body(ErrorResponse.of(
                        HttpResponse.Fail.INVALID_INPUT_VALUE.getMessage(),
                        request
                ));
    }

    /**
     * HTTP 관련 Exception Handler
     */
    @ExceptionHandler({
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpMessageNotReadableException.class,
    })
    public ResponseEntity<ErrorResponse> handleHttpException(Exception e, HttpServletRequest request) {
        HttpResponse.Fail response;

        String exceptionName = e.getClass().getSimpleName();
        switch (exceptionName) {
            case "HttpRequestMethodNotSupportedException" -> response = HttpResponse.Fail.METHOD_NOT_ALLOWED;
            case "AccessDeniedException" -> response = HttpResponse.Fail.DEACTIVATE_USER;
            default -> response = HttpResponse.Fail.BAD_REQUEST;
        }

        return ResponseEntity
                .status(response.getStatus())
                .body(ErrorResponse.of(
                        response.getMessage(),
                        request
                ));
    }

    /**
     * 설정하지 않은 Exception 처리 Handler
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handlerAllException(Exception e, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpResponse.Fail.INTERNAL_SERVER_ERROR.getStatus())
                .body(ErrorResponse.of(
                        HttpResponse.Fail.INTERNAL_SERVER_ERROR.getMessage(),
                        request,
                        e
                ));
    }
}
