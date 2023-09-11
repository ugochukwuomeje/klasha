package com.ugochukwu.countriesapi.util;

import com.ugochukwu.countriesapi.dto.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.logging.Logger;

@Component
public class ProcessRequestImpl implements ProcessRequest{

    Logger log = null;
    public ProcessRequestImpl(){

        log = Logger.getLogger(ProcessRequestImpl.class.getName());
    }
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public ResponseEntity<?> getCountryInfo(String url, HttpEntity request) throws SocketTimeoutException, IOException, Exception {

        ResponseEntity<?> response = null;
        log.info(":::::::::::::::::::the calling processedRequest population method");

         response  = restTemplate.exchange(url, HttpMethod.GET, request, CityResponse.class);

          log.info(":::::::::::the http response code: "+response.getStatusCode().value());
          log.info(":::::::::::the http response msg: "+response.getStatusCode().name());
          //log.info("::::::::::the http response message is: "+response.);


        log.info(":::::::::::the http response code: "+response.getStatusCode().value());
        log.info(":::::::::::the http response msg: "+response.getStatusCode().name());


        return response;


    }

    @Override
    public ResponseEntity<?> getCountryStateCity(String url, HttpEntity request) throws SocketTimeoutException, IOException, Exception {

        ResponseEntity<?> response = null;
        log.info(":::::::::::::::::::the calling processedRequest country method");

        response  = restTemplate.exchange(url, HttpMethod.GET, request, CountryStatesResponse.class);

        log.info(":::::::::::the http response code: "+response.getStatusCode().value());
        log.info(":::::::::::the http response msg: "+response.getStatusCode().name());
        //log.info("::::::::::the http response message is: "+response.);


        log.info(":::::::::::the http response code: "+response.getStatusCode().value());
        log.info(":::::::::::the http response msg: "+response.getStatusCode().name());

        return response;


    }

    @Override
    public ResponseEntity<?> getStateCity(String url, HttpEntity request) throws SocketTimeoutException, IOException, Exception {

        ResponseEntity<?> response = null;
        log.info(":::::::::::::::::::the calling processedRequest cities in state method");

        response  = restTemplate.exchange(url, HttpMethod.GET, request, StateCitiesResponse.class);

        log.info(":::::::::::the http response code: "+response.getStatusCode().value());
        log.info(":::::::::::the http response msg: "+response.getStatusCode().name());
        //log.info("::::::::::the http response message is: "+response.);

        log.info(":::::::::::the http response code: "+response.getStatusCode().value());
        log.info(":::::::::::the http response msg: "+response.getStatusCode().name());

        return response;


    }


    @Override
    public ResponseEntity<?> getCountryPopulation(String url, HttpEntity request) throws SocketTimeoutException, IOException, Exception {
        ResponseEntity<?> response = null;
        log.info(":::::::::::::::::::the calling processedRequest list population method");

        response  = restTemplate.exchange(url, HttpMethod.GET, request, PopulationResponse.class);

        log.info(":::::::::::the http response code: "+response.getStatusCode().value());
        log.info(":::::::::::the http response msg: "+response.getStatusCode().name());
        //log.info("::::::::::the http response message is: "+response.);


        log.info(":::::::::::the http response code: "+response.getStatusCode().value());
        log.info(":::::::::::the http response msg: "+response.getStatusCode().name());


        return response;
    }

    @Override
    public ResponseEntity<?> getLocation(String url, HttpEntity request) throws SocketTimeoutException, IOException, Exception {
        ResponseEntity<?> response = null;
        log.info(":::::::::::::::::::the calling processedRequest location method");

        response  = restTemplate.exchange(url, HttpMethod.GET, request, LocationResponse.class);

        log.info(":::::::::::the http response code: "+response.getStatusCode().value());
        log.info(":::::::::::the http response msg: "+response.getStatusCode().name());
        //log.info("::::::::::the http response message is: "+response.);


        log.info(":::::::::::the http response code: "+response.getStatusCode().value());
        log.info(":::::::::::the http response msg: "+response.getStatusCode().name());


        return response;
    }

    @Override
    public ResponseEntity<?> getCapital(String url, HttpEntity request) throws SocketTimeoutException, IOException, Exception {
        ResponseEntity<?> response = null;
        log.info(":::::::::::::::::::the calling processedRequest capital method");

        response  = restTemplate.exchange(url, HttpMethod.GET, request, CapitalResponse.class);

        log.info(":::::::::::the http response code: "+response.getStatusCode().value());
        log.info(":::::::::::the http response msg: "+response.getStatusCode().name());
        //log.info("::::::::::the http response message is: "+response.);


        log.info(":::::::::::the http response code: "+response.getStatusCode().value());
        log.info(":::::::::::the http response msg: "+response.getStatusCode().name());


        return response;
    }

    @Override
    public ResponseEntity<?> getCurrency(String url, HttpEntity request) throws SocketTimeoutException, IOException, Exception {
        ResponseEntity<?> response = null;
        log.info(":::::::::::::::::::the calling processedRequest currency method");

        response  = restTemplate.exchange(url, HttpMethod.GET, request, CurrencyResponse.class);

        log.info(":::::::::::the http response code: "+response.getStatusCode().value());
        log.info(":::::::::::the http response msg: "+response.getStatusCode().name());
        //log.info("::::::::::the http response message is: "+response.);


        log.info(":::::::::::the http response code: "+response.getStatusCode().value());
        log.info(":::::::::::the http response msg: "+response.getStatusCode().name());


        return response;
    }

    @Override
    public ResponseEntity<?> getiSo(String url, HttpEntity request) throws SocketTimeoutException, IOException, Exception {
        ResponseEntity<?> response = null;
        log.info(":::::::::::::::::::the calling processedRequest iso method");

        response  = restTemplate.exchange(url, HttpMethod.GET, request, CountryInfoIso.class);

        log.info(":::::::::::the http response code: "+response.getStatusCode().value());
        log.info(":::::::::::the http response msg: "+response.getStatusCode().name());
        //log.info("::::::::::the http response message is: "+response.);


        log.info(":::::::::::the http response code: "+response.getStatusCode().value());
        log.info(":::::::::::the http response msg: "+response.getStatusCode().name());


        return response;
    }


}
