package com.tcg.mis.controller.handler;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.tcg.mis.common.constant.ErrorCode;
import com.tcg.mis.common.exception.MisBaseException;
import com.tcg.mis.common.exception.TimeoutException;
import com.tcg.mis.common.log.TcgLogFactory;
import com.tcg.mis.common.response.BaseResponse;

/**
 * Global exception handler. <p/>
 * See <a href="https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc">Spring exception handling</a> <p/>
 * Created by Eddie on 2016/5/11.
 */
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    private final static Logger LOGGER = TcgLogFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> handleException(Exception ex) {
        BaseResponse response = new BaseResponse(false, ErrorCode.SYS_ERR, ex.getMessage());

        LOGGER.error(ex.getMessage(), ex);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseResponse> handleValidationException(ConstraintViolationException e) {
        List<String> msgList = new ArrayList<>();
        for (ConstraintViolation<?> s : e.getConstraintViolations()) {
            msgList.add(s.getMessage());
        }

        String msg = StringUtils.join(msgList, "; ");
        BaseResponse response = new BaseResponse(ErrorCode.REQ_ERR, msg);

        LOGGER.warn(msg);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    public ResponseEntity<BaseResponse> handleException(ServletRequestBindingException ex) {
        BaseResponse response = new BaseResponse(ErrorCode.REQ_ERR, ex.getMessage());
        LOGGER.warn(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<BaseResponse> handleException(MethodArgumentTypeMismatchException ex) {

        String message = "The parameter " + ex.getName() + " must be correct type.";

        BaseResponse response = new BaseResponse(ErrorCode.REQ_ERR, message);

        LOGGER.warn(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse> handleException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<org.springframework.validation.FieldError> fieldErrors = result.getFieldErrors();
        String message = processFieldErrors(fieldErrors);

        BaseResponse response = new BaseResponse(ErrorCode.REQ_ERR, message);
        LOGGER.warn(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private String processFieldErrors(List<org.springframework.validation.FieldError> fieldErrors) {
        StringBuilder builder = new StringBuilder();
        for (FieldError error : fieldErrors) {
            builder.append(error.getField())
                   .append(": ")
                   .append(error.getDefaultMessage())
                   .append(", ");
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 2);
        }
        return StringUtils.trim(builder.toString());
    }


    @ExceptionHandler(BindException.class)
    public ResponseEntity<BaseResponse> handleException(BindException ex) {
        List<FieldError> errors = ex.getFieldErrors();

        String message = processFieldErrors(errors);

        BaseResponse response = new BaseResponse(ErrorCode.REQ_ERR, message);
        LOGGER.error(message, ex);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TimeoutException.class)
    public ResponseEntity<BaseResponse> handleException(TimeoutException ex) {
        LOGGER.error(ex.getMessage(), ex);

        String message = ex.getMessage();
        String errorCode = ex.getErrorCode().getCode();
        if (StringUtils.isNotBlank(errorCode) && StringUtils.isNotBlank(message)) {
            String[] temp = message.split("\\[" + errorCode + "] ");
            message = temp[temp.length - 1].trim();
        }

        BaseResponse response = new BaseResponse(ex.getErrorCode(), message);
        return new ResponseEntity<>(response, HttpStatus.REQUEST_TIMEOUT);
    }
    
    @ExceptionHandler(MisBaseException.class)
    public ResponseEntity<BaseResponse> handleException(MisBaseException ex) {
        LOGGER.error(ex.getMessage(), ex);

        String message = ex.getMessage();
        String errorCode = ex.getErrorCode().getCode();
        if (StringUtils.isNotBlank(errorCode) && StringUtils.isNotBlank(message)) {
            String[] temp = message.split("\\[" + errorCode + "] ");
            message = temp[temp.length - 1].trim();
        }

        BaseResponse response = new BaseResponse(ex.getErrorCode(), message);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
