package com.pavan.api_gateway.exception;

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
