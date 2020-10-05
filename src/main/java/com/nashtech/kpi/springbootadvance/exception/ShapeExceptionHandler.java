package com.nashtech.kpi.springbootadvance.exception;

import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@SuppressWarnings({"unchecked","rawtypes"})
@ControllerAdvice
public class ShapeExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ShapeException.class)
    public final ResponseEntity<Object> handleShapeException (ShapeException shapeException) {
        ErrorMessage errorMessage = new ErrorMessage(shapeException.getLocalizedMessage());
        return new ResponseEntity<Object>(errorMessage, HttpStatus.NOT_FOUND);
    }
}
