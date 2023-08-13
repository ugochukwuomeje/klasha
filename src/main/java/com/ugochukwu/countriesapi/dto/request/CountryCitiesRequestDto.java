package com.ugochukwu.countriesapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryCitiesRequestDto {

    private String country;
    private String state;
}
