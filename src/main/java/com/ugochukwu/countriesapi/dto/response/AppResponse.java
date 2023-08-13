package com.ugochukwu.countriesapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppResponse<T> implements Serializable {

    private String status;
    private String message;

    private T data;

//    private boolean error;
//
//    private String msg;
//
//    private T data;

}
