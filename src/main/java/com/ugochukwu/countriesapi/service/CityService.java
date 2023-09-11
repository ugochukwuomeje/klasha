package com.ugochukwu.countriesapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.ugochukwu.countriesapi.dto.request.CountryCitiesRequestDto;
import com.ugochukwu.countriesapi.dto.request.CountryCityPopulationRequest;
import com.ugochukwu.countriesapi.dto.request.CountryInfoRequestDto;
import com.ugochukwu.countriesapi.dto.response.*;
import com.ugochukwu.countriesapi.enums.StatusMessage;
import com.ugochukwu.countriesapi.util.ProcessRequest;
import com.ugochukwu.countriesapi.util.ProcessRequestImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CityService {

    private final RestTemplate restTemplate;

    @Autowired
    private ProcessRequest processRequest;

    @Value("${countriesapi.populatedcity}")
    private String countryCityPopulationUrl;

    @Value("${countriesapi.population}")
    private String populationurl;
    @Value("${countriesapi.location}")
    private String countrylocationUrl;

    @Value("${countriesapi.country_capital}")
    private String country_capital_url;

    @Value("${countriesapi.currency}")
    private String countrycurrencyUrl;

    @Value("${countriesapi.country_iso}")
    private String countryisourl;

    @Value("${countriesapi.states}")
    private String countrystatecyUrl;

    @Value("${countriesapi.cities}")
    private String state_cities_url;

    Gson gson = null;

    @Autowired
    public CityService(RestTemplate restTemplate ) {
        this.restTemplate = restTemplate;
        gson = new Gson();
    }

    public ResponseEntity<CityResponse> getTopCitiesByPopulation(String country, Integer numOfCities) {

        log.info(":::::::::::::::::::::::calling get top cities service method");
        List<CityResponse> allCities = new ArrayList<>();

        CityResponse cityResponse = new CityResponse();

        ObjectMapper ob = new ObjectMapper();

           HttpHeaders httpHeaders = new HttpHeaders();

           httpHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

           HttpEntity requestEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<CityResponse> rawResponse = null;
        ResponseEntity<CityResponse> response = null;

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(countryCityPopulationUrl)
                .queryParam("limit",numOfCities)
                .queryParam("order","dsc")
                .queryParam("orderBy","population")
                .queryParam("country",country);

            log.info(":::::::::::::::: the request sent for getting most populated cities is: "+uriComponentsBuilder.toUriString());
       try {
           log.info(" get most populated city URL => " + countryCityPopulationUrl);
           rawResponse = (ResponseEntity<CityResponse>) processRequest.getCountryInfo(uriComponentsBuilder.toUriString(), requestEntity);
       } catch (SocketTimeoutException e) {
            e.printStackTrace();
           cityResponse.setError(true);
           cityResponse.setMessage(StatusMessage.TIMEOUT.toString());
           response = new ResponseEntity<>(cityResponse, HttpStatus.REQUEST_TIMEOUT);
       } catch (IOException e) {
           e.printStackTrace();
           cityResponse.setError(true);
           cityResponse.setMessage(StatusMessage.REQUEST_CANNOT_BE_PROCESSED_CURRENTLY.toString());
           response = new ResponseEntity<>(cityResponse, HttpStatus.BAD_REQUEST);
       } catch (Exception e) {
           e.printStackTrace();
           cityResponse.setError(true);
           cityResponse.setMessage(StatusMessage.INTERNAL_SERVER_ERROR.toString());
           response = new ResponseEntity<>(cityResponse, HttpStatus.INTERNAL_SERVER_ERROR);
       }

       if(rawResponse != null) {
           if(rawResponse.getStatusCode().value() == HttpStatus.OK.value()) {

                   log.info("::::::::::::::::::the cityresponse result is: "+rawResponse.getBody());
                   //cityResponse =  gson.fromJson(rawResponse.getBody().toString(), CityResponse.class);
                   cityResponse =  rawResponse.getBody();

               cityResponse.setMessage(StatusMessage.SUCCESS.toString());
               response  = new ResponseEntity<>(cityResponse, HttpStatus.OK);
           }else{

                   cityResponse = gson.fromJson(String.valueOf(rawResponse.getBody()), CityResponse.class);

               cityResponse.setMessage(rawResponse.getBody().getMessage());
               response = new ResponseEntity<>(cityResponse, HttpStatus.BAD_REQUEST);
           }
       }else{
           log.info(":::::::::::::::::::::::empty response returned");
           cityResponse.setError(true);
           cityResponse.setMessage(StatusMessage.REQUEST_CANNOT_BE_PROCESSED_CURRENTLY.toString());
           response = new ResponseEntity<>(cityResponse, HttpStatus.NO_CONTENT);
       }

       return  response;

    }

    public CountryInformation getCountryInformation(String country){

        log.info(":::::::::::::::::::::::call get the country "+country+" info service method");

        CountryInformation countryInformation = new CountryInformation();

        countryInformation.setCountry(country);
        countryInformation.setPopulation(getCountryPopulation(country));
        countryInformation.setCapital(getCountryCapital(country));
        countryInformation.setLocation(getCountryLocation(country));
        countryInformation.setCurrency(getCountryCurrency(country));
        countryInformation.setIso(getCountryISO(country));

        return countryInformation;

    }

    public CountryInfoIso getCountryISO(String country) {

        log.info(":::::::::::::::::::get "+country+" iso2 and iso3");
        CountryInfoIso countryInfoIso = new CountryInfoIso();

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity requestEntity = new HttpEntity<>(httpHeaders);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(countryisourl)
                .queryParam("country",country);

        ResponseEntity<CountryInfoIso> rawResponse = null;
        try {
            log.info(" get country iso URL => " + countryisourl);
            rawResponse =  (ResponseEntity<CountryInfoIso>) processRequest.getiSo(uriComponentsBuilder.toUriString(), requestEntity);
        } catch (SocketTimeoutException e) {
            log.info("::::::::::::::::time out");
            e.printStackTrace();
            countryInfoIso = null;

        } catch (IOException e) {
            e.printStackTrace();
            countryInfoIso = null;
        } catch (Exception e) {
            e.printStackTrace();
            countryInfoIso = null;
        }

        if(rawResponse != null) {
            if(rawResponse.getStatusCode().value() == HttpStatus.OK.value()) {
                countryInfoIso.setIso2(rawResponse.getBody().getIso2());
                countryInfoIso.setIso3(rawResponse.getBody().getIso3());

            }else{
                log.info("::::::::::::::::::::http status response code"+rawResponse.getStatusCode().value());
                countryInfoIso = null;
            }
        }else{
            log.info(":::::::::::::::::::::::empty response returned");
            countryInfoIso = null;
        }

        return  countryInfoIso;

    }


    public String getCountryCurrency(String country) {

        log.info(":::::::::::::::::::::::get "+country+" currency");

        String currency = null;

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity requestEntity = new HttpEntity<>(httpHeaders);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(countrycurrencyUrl)
                .queryParam("country",country);

        ResponseEntity<CurrencyResponse> currencyResponse = null;
        try {
            log.info(" get country iso URL => " + countryisourl);
            currencyResponse =  (ResponseEntity<CurrencyResponse>) processRequest.getCurrency(uriComponentsBuilder.toUriString(), requestEntity);
        } catch (SocketTimeoutException e) {
            log.info("::::::::::::::::time out");
            e.printStackTrace();
            return null;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        if(currencyResponse != null) {
            if(currencyResponse.getStatusCode().value() == HttpStatus.OK.value()) {
                currency = currencyResponse.getBody().getData().getCurrency();
                log.info("::::::::::::::::::::::;the currency is: "+currency);
            }else{
                currency = null;
            }
        }else{
            log.info(":::::::::::::::::::::::empty response returned");
            currency = null;
        }

        return  currency;
    }


    public CountryLocation getCountryLocation(String country) {

        log.info(":::::::::::::::::::::::get "+country+" location");
        CountryLocation countryInfoLocation = new CountryLocation();

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity requestEntity = new HttpEntity<>(httpHeaders);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(countrylocationUrl)
                .queryParam("country",country);

        ResponseEntity<LocationResponse> rawResponse = null;
        try {
            log.info(" get country iso URL => " + countryisourl);
            rawResponse =  (ResponseEntity<LocationResponse>) processRequest.getLocation(uriComponentsBuilder.toUriString(), requestEntity);
        } catch (SocketTimeoutException e) {
            log.info("::::::::::::::::time out");
            e.printStackTrace();
            countryInfoLocation = null;

        } catch (IOException e) {
            e.printStackTrace();
            countryInfoLocation = null;
        } catch (Exception e) {
            e.printStackTrace();
            countryInfoLocation = null;
        }

        if(rawResponse != null) {
            if(rawResponse.getStatusCode().value() == HttpStatus.OK.value()) {
                countryInfoLocation.setLongitude(rawResponse.getBody().getData().getLongitude());
                countryInfoLocation.setLat(rawResponse.getBody().getData().getLat());

            }else{
                log.info(":::::::::::::::::::the http status response code: "+rawResponse.getStatusCode().value());
                countryInfoLocation = null;
            }
        }else{
            log.info(":::::::::::::::::::::::empty response returned");
            countryInfoLocation = null;
        }

        return  countryInfoLocation;

    }


    public String getCountryCapital(String country) {

        log.info(":::::::::::::::::::::::get "+country+" capital");
        String capital = null;

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity requestEntity = new HttpEntity<>(httpHeaders);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(country_capital_url)
                .queryParam("country",country);

        ResponseEntity<CapitalResponse> capitalResponseResponseEntity = null;
        try {
            log.info(" get country iso URL => " + countryisourl);
            capitalResponseResponseEntity =  (ResponseEntity<CapitalResponse>) processRequest.getCapital(uriComponentsBuilder.toUriString(), requestEntity);
        } catch (SocketTimeoutException e) {
            log.info("::::::::::::::::time out");
            e.printStackTrace();
            return null;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        if(capitalResponseResponseEntity != null) {
            if(capitalResponseResponseEntity.getStatusCode().value() == HttpStatus.OK.value()) {
                capital = capitalResponseResponseEntity.getBody().getData().getCapital();
                log.info("::::::::::::::::::::::;the currency is: "+capital);
            }else{
                capital = null;
            }
        }else{
            log.info(":::::::::::::::::::::::empty response returned");
            capital = null;
        }

        return  capital;
    }


    public List<PopulationCounts> getCountryPopulation(String country) {

        log.info(":::::::::::::::::::::::get "+country+" population");
        List<PopulationCounts> countryInfoPopulation = null;

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity requestEntity = new HttpEntity<>(httpHeaders);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(populationurl)
                .queryParam("country",country);

        ResponseEntity<PopulationResponse> rawResponse = null;
        try {
            log.info(" get country population URL => " + populationurl);
            rawResponse =  (ResponseEntity<PopulationResponse>) processRequest.getCountryPopulation(uriComponentsBuilder.toUriString(), requestEntity);
        } catch (SocketTimeoutException e) {
            log.info("::::::::::::::::time out");
            e.printStackTrace();
            countryInfoPopulation = null;

        } catch (IOException e) {
            e.printStackTrace();
            countryInfoPopulation = null;
        } catch (Exception e) {
            e.printStackTrace();
            countryInfoPopulation = null;
        }

        if(rawResponse != null) {
            if(rawResponse.getStatusCode().value() == HttpStatus.OK.value()) {
                countryInfoPopulation = rawResponse.getBody().getData().getPopulationCounts();

            }else{
                log.info(":::::::::::::::::::the http status response code: "+rawResponse.getStatusCode().value());
                countryInfoPopulation = null;
            }
        }else{
            log.info(":::::::::::::::::::::::empty response returned");
            countryInfoPopulation = null;
        }

        return  countryInfoPopulation;
    }

    public CountryStatesCities getCountryStatesAndCities(String country) {

        log.info(":::::::::::::::::::::::get " + country + " states");

        HttpHeaders httpHeaders = new HttpHeaders();

        CountryStatesResponse countryStatesResponse = null;

        final CountryStatesCities countryStatesCities = new CountryStatesCities();

        httpHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity requestEntity = new HttpEntity<>(httpHeaders);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(countrystatecyUrl)
                .queryParam("country", country);

        ResponseEntity<CountryStatesResponse> stateResponseResponseEntity = null;
        try {
            log.info(" get country iso URL => " + countrystatecyUrl);
            stateResponseResponseEntity = (ResponseEntity<CountryStatesResponse>) processRequest.getCountryStateCity(uriComponentsBuilder.toUriString(), requestEntity);

            if(stateResponseResponseEntity == null){

                countryStatesCities.setMessage(StatusMessage.NO_RESPONSE.toString());

                return countryStatesCities;
            }else if(stateResponseResponseEntity.getStatusCode().value() !=  HttpStatus.OK.value()){


                countryStatesCities.setMessage(stateResponseResponseEntity.getBody().getMsg());

                return countryStatesCities;
            }

            countryStatesResponse = stateResponseResponseEntity.getBody();

                Map<String, List<String>> cities = new HashMap<String, List<String>>();

            countryStatesResponse.getData().getStates().forEach(state -> {
                    cities.put(state.getName(), getCitiesInState(country, state.getName()));
                countryStatesCities.setCities(cities);
                });

            countryStatesCities.setCountry(country);


        } catch (SocketTimeoutException e) {
            e.printStackTrace();

            countryStatesCities.setMessage(StatusMessage.TIMEOUT.toString());
        } catch (IOException e) {
            e.printStackTrace();

            countryStatesCities.setMessage(StatusMessage.REQUEST_CANNOT_BE_PROCESSED_CURRENTLY.toString());
        } catch (Exception e) {
            e.printStackTrace();

            countryStatesCities.setMessage(StatusMessage.REQUEST_CANNOT_BE_PROCESSED_CURRENTLY.toString());
        }

        return countryStatesCities;

    }


    public List<String> getCitiesInState(String country, String state){

        log.info(":::::::::::::::::::::::get cities in" + country + " states "+state);

        HttpHeaders httpHeaders = new HttpHeaders();

        List<String> erroStringList = null;

        httpHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity requestEntity = new HttpEntity<>(httpHeaders);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(state_cities_url)
                .queryParam("country", country)
                .queryParam("state",state);

        ResponseEntity<CountryStatesResponse> stateResponseResponseEntity = null;

        ResponseEntity<StateCitiesResponse> response = null;
        try {
            response = (ResponseEntity<StateCitiesResponse>)processRequest.getStateCity(uriComponentsBuilder.toUriString(), requestEntity);
        } catch (Exception e) {
             erroStringList = new ArrayList<>();
            log.info(":::::::::::::::::::::::cities not found");
            erroStringList.add(StatusMessage.CITIES_IN_STATE_NOT_FOUND.toString());
            e.printStackTrace();
            return erroStringList;
        }

        log.info("Cities in state: "+response.getBody().getData().toString());

            if (response != null && response.getBody()!=null) {

                return response.getBody().getData();
            }

        List<String> stringList = new ArrayList<>();
        stringList.add(StatusMessage.CITIES_IN_STATE_NOT_FOUND.toString());

        return stringList;
        }



}
