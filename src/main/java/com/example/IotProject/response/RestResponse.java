package com.example.IotProject.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RestResponse<T> {
    private int statusCode;
    private String error;
    private Object message;
    private T data;

}