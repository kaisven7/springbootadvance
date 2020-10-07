package com.nashtech.kpi.springbootadvance.exception;

import com.nashtech.kpi.springbootadvance.exception.error.CarsException;
import com.nashtech.kpi.springbootadvance.exception.error.UsersException;
import com.nashtech.kpi.springbootadvance.model.response.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked","rawtypes"})
@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = CarsException.class)
    public final ResponseEntity<Object> handleCarsNotFoundException(CarsException exception) {
        List<String> details = new ArrayList<>();
        details.add(exception.getLocalizedMessage());
        ErrorMessage errorMessage = new ErrorMessage("Cars Service Error !!!", details);
        return new ResponseEntity<Object>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UsersException.class)
    public final ResponseEntity<Object> handleCarsNotFoundException(UsersException exception) {
        List<String> details = new ArrayList<>();
        details.add(exception.getLocalizedMessage());
        ErrorMessage errorMessage = new ErrorMessage("Users Service Error !!!", details);
        return new ResponseEntity<Object>(errorMessage, HttpStatus.NOT_FOUND);
    }
}
