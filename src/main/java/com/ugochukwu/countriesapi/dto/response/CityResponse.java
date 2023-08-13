package com.ugochukwu.countriesapi.dto.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityResponse {
    private boolean error;
    @JsonAlias("msg")
    private String message;
    private List<CityData> data;
}
