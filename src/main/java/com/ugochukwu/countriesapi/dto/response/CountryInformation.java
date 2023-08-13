package com.ugochukwu.countriesapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryInformation {

    private String country;
    private List<PopulationCounts> population;
    private String capital;
    private CountryLocation  location;
    private String currency;
    private CountryInfoIso iso;
}
