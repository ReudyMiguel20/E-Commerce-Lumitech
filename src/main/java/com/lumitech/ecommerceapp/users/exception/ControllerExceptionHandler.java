package com.lumitech.ecommerceapp.users.exception;

import com.lumitech.ecommerceapp.product.exception.CustomErrorMessage;
import com.lumitech.ecommerceapp.product.exception.errors.ProductIsEmptyOrNullException;
import com.lumitech.ecommerceapp.users.exception.error.UserAlreadyExists;
import com.lumitech.ecommerceapp.users.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {


    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        //Initializing the HttpServletRequest object to get the path of the request that caused the error.
        HttpServletRequest requestServlet = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String path = requestServlet.getRequestURI();

        //Creating the body of the response
        com.lumitech.ecommerceapp.product.exception.CustomErrorMessage customErrorMessage = new CustomErrorMessage(
                LocalDateTime.now(),
                status.value(),
                status.toString(),
                "The request contains invalid data, probably left some field empty or null.",
                path
        );

        return ResponseEntity.badRequest().body(customErrorMessage);
    }

    @ExceptionHandler(UserAlreadyExists.class)
    public static ResponseEntity<CustomErrorMessage> handleProductEmptyOrNull() {
        //Initializing the HttpServletRequest object to get the path of the request that caused the error.
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String path = request.getRequestURI();

        CustomErrorMessage customErrorMessage = new CustomErrorMessage(
                LocalDateTime.now(),
                409,
                "Conflict",
                "There's an user with the same email already registered in the system.",
                path
        );
        return ResponseEntity.status(409).body(customErrorMessage);
    }

}
