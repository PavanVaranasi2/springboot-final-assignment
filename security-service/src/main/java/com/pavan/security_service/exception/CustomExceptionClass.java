package com.pavan.security_service.exception;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomExceptionClass {
    private int status;
    private String message;
    private String timestamp;
}
