package com.sha.springbootrest.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * @author sa
 * @date 2020-04-11
 * @time 20:15
 */
@Slf4j //Lombok logger
@ControllerAdvice
public class ControllerExceptionHandler
{

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public RuntimeException handleValidationErrorRequest(HttpServletRequest req, MethodArgumentNotValidException ex)
    {
        log.error("Request Parameter Validation Exception occurred on path:{} ", req.getRequestURI(), ex);

        return new RuntimeException(ex.getMessage());
    }
}
