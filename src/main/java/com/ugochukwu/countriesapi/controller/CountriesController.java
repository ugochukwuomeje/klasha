package com.ugochukwu.countriesapi.controller;

import com.ugochukwu.countriesapi.dto.request.AuthenticationRequest;
import com.ugochukwu.countriesapi.dto.response.CityResponse;
import com.ugochukwu.countriesapi.dto.response.CountryInformation;
import com.ugochukwu.countriesapi.dto.response.CountryStatesCities;
import com.ugochukwu.countriesapi.dto.response.JwtResponse;
import com.ugochukwu.countriesapi.service.CityService;
import com.ugochukwu.countriesapi.service.CurrencyConversionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
@Api(tags = "Countries-api")
public class CountriesController {

    private final CityService cityService;
    private final CurrencyConversionService currencyConversionService;

    @GetMapping("/top-cities")
    public ResponseEntity<CityResponse> getTopCities(@RequestParam Integer numberOfCity, @RequestParam String country) {

        log.info("::::::::::::::::::calling get top cities controller");
        CityResponse topCities;

        return cityService.getTopCitiesByPopulation(country, numberOfCity);

        //return new ResponseEntity<>(topCities, HttpStatus.OK);
    }

    @GetMapping("/country/information")
    public ResponseEntity<CountryInformation> getCountryInformation(@RequestParam String country) {

        log.info(":::::::::::::::::calling country information controller method");
        CountryInformation countryInformation = cityService.getCountryInformation(country);

        if (countryInformation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(countryInformation, HttpStatus.OK);
    }

    @GetMapping("/country/states/cities")
    public ResponseEntity<CountryStatesCities> getCountryStatesAndCities(@RequestParam String country) {
        CountryStatesCities countryStatesCities = cityService.getCountryStatesAndCities(country);

        if (countryStatesCities == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(countryStatesCities, HttpStatus.OK);
    }

//    @GetMapping("/country/states/listcities")
//    public ResponseEntity<List<String>> getStatesAndCities(@RequestParam String country,@RequestParam String state) {
//        List<String> countryStatesCities = cityService.getCitiesInState(country, state);
//
//        if (countryStatesCities == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        return new ResponseEntity<>(countryStatesCities, HttpStatus.OK);
//    }

    @GetMapping("/country/convert-currency")
    public ResponseEntity<String> convertCurrency(
            @RequestParam("country") String country,
            @RequestParam("amount") BigDecimal amount,
            @RequestParam("targetCurrency") String targetCurrency) {


        String response = currencyConversionService.convertCurrency(targetCurrency, amount,country);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
