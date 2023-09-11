package com.ugochukwu.countriesapi.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CurrencyConversionService {

    private final Map<String, Map<String, BigDecimal>> exchangeRates;//final

    @Autowired
    private CityService cityService;

    public CurrencyConversionService() {
        this.exchangeRates = loadExchangeRates();
    }

    private Map<String, Map<String, BigDecimal>> loadExchangeRates() {
        Map<String, Map<String, BigDecimal>> rates = new HashMap<>();
        try (CSVReader reader = new CSVReader(new FileReader("exchange_rate.csv"))) {
            List<String[]> rows = reader.readAll();
            for (String[] row : rows) {
                String baseCurrency = row[0];
                String targetCurrency = row[1];
                BigDecimal exchangeRate = new BigDecimal(row[2]);

                rates.computeIfAbsent(baseCurrency, k -> new HashMap<>())
                        .put(targetCurrency, exchangeRate);
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return rates;
    }

    public String convertCurrency(String targetCurrency, BigDecimal amount, String country) {

        log.info("::::::::::::::::::::::::::: the target country is: "+country);
        log.info("country: "+country+"amount: "+amount+" currency: "+targetCurrency);
        String baseCurrency = cityService.getCountryCurrency(country);
        log.info("Base currency: "+baseCurrency);

        if (baseCurrency.equals(targetCurrency)) {

            String response = String.format("%s %.2f = %s %.2f", baseCurrency, amount, targetCurrency, amount);
            return response;
        }

        log.info(exchangeRates.toString());
        Map<String, BigDecimal> baseCurrencyRates = exchangeRates.get(baseCurrency);
        if (baseCurrencyRates == null) {
            throw new IllegalArgumentException("Base currency not found: " + baseCurrency);
        }

        BigDecimal exchangeRate = baseCurrencyRates.get(targetCurrency);
        if (exchangeRate == null) {
            throw new IllegalArgumentException("Target currency not found: " + targetCurrency);
        }

        BigDecimal convertedAmount = amount.multiply(exchangeRate);

        return convertedAmount.toString();
    }
}
