package com.ugochukwu.countriesapi.util;

import com.ugochukwu.countriesapi.dto.request.CountryCityPopulationRequest;
import com.ugochukwu.countriesapi.dto.request.CountryInfoRequestDto;
import com.ugochukwu.countriesapi.dto.response.CityResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;

public interface ProcessRequest {


    public ResponseEntity<?> getCountryInfo(String url,  HttpEntity request) throws SocketTimeoutException, IOException, Exception;

    ResponseEntity<?> getCountryStateCity(String url, HttpEntity request) throws SocketTimeoutException, IOException, Exception;

    ResponseEntity<?> getStateCity(String url, HttpEntity request) throws SocketTimeoutException, IOException, Exception;

    public ResponseEntity<?> getCountryPopulation(String url, HttpEntity request) throws SocketTimeoutException, IOException, Exception;

    public ResponseEntity<?> getLocation(String url,  HttpEntity request) throws SocketTimeoutException, IOException, Exception;

    public ResponseEntity<?> getCapital(String url,  HttpEntity request) throws SocketTimeoutException, IOException, Exception;
    public ResponseEntity<?> getCurrency(String url,  HttpEntity request) throws SocketTimeoutException, IOException, Exception;
    public ResponseEntity<?> getiSo(String url,  HttpEntity request) throws SocketTimeoutException, IOException, Exception;
}
