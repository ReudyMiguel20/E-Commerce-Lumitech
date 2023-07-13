package com.lumitech.ecommerceapp.common.exception;


import com.lumitech.ecommerceapp.common.exception.errors.ItemExistsException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerExceptionHandler {


    //Need to check the output in postman of this, fix this tomorrow
    @ExceptionHandler(ItemExistsException.class)
    public static ResponseEntity<CustomErrorMessage> handleItemExists() {
    /* This is also a good way to get the path, it is more abstract and requires a WebRequest object on the PARAMETER of the METHOD,
       but I need to manipulate the path to get the result I want. Keeping it here for future reference */
//      String path = request.getDescription(false);
//      path = path.replace("uri=", "");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String path = request.getRequestURI();

        CustomErrorMessage customErrorMessage = new CustomErrorMessage(
                LocalDateTime.now(),
                400,
                "Bad Request",
                "Item already exists",
                 path
        );
        return ResponseEntity.badRequest().body(customErrorMessage);
    }
}
