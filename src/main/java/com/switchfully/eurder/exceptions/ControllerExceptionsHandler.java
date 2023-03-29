package com.switchfully.eurder.exceptions;

import com.switchfully.eurder.exceptions.exceptions.CustomerNotFoundException;
import com.switchfully.eurder.exceptions.exceptions.ItemNotFoundException;
import com.switchfully.eurder.exceptions.exceptions.UnauthorizedAccessException;
import com.switchfully.eurder.exceptions.exceptions.UserAlreadyExistsException;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class ControllerExceptionsHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(ControllerExceptionsHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    protected void illegalArgumentException(IllegalArgumentException ex, HttpServletResponse response) throws IOException{
        logger.error(ex.toString());
        response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    protected void customerNotFoundException(CustomerNotFoundException ex, HttpServletResponse response) throws IOException{
        logger.error(ex.toString());
        response.sendError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(ItemNotFoundException.class)
    protected void itemNotFoundException(ItemNotFoundException ex, HttpServletResponse response) throws IOException{
        logger.error(ex.toString());
        response.sendError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    protected void unauthorizedAccessException(UnauthorizedAccessException ex, HttpServletResponse response) throws IOException{
        logger.error(ex.toString());
        response.sendError(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    protected void userAlreadyExistsException(UserAlreadyExistsException ex, HttpServletResponse response) throws IOException{
        logger.error(ex.toString());
        response.sendError(HttpStatus.FORBIDDEN.value(), ex.getMessage());
    }
}
