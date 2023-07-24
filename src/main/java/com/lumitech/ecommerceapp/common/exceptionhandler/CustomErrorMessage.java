package com.lumitech.ecommerceapp.common.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomErrorMessage {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

}
