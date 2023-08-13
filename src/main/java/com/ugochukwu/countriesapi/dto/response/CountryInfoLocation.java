package com.ugochukwu.countriesapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryInfoLocation {

    private double lat;
    private double  longitude;
}
