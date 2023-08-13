package com.ugochukwu.countriesapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryInfoIso {

    private String iso2;
    private String iso3;
}
