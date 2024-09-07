package com.paymint.exchangerate;

import com.paymint.exchangerate.ExchangeRate;
import com.paymint.exchangerate.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ExchangeRateService {

    private static final String EXCHANGE_RATE_API_URL = 
        "https://api.exchangeratesapi.io/v1/latest?access_key=$ACCESS_TOKEN$";

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    private RestTemplate restTemplate;

    // Method to fetch exchange rates from the API and store them in the database
    @SuppressWarnings("unchecked")
    public void fetchAndStoreExchangeRates() {
        Map<String, Object> response = restTemplate.getForObject(EXCHANGE_RATE_API_URL, Map.class);
        
        if (response == null || !response.containsKey("rates")) {
            throw new RuntimeException("Unable to fetch exchange rates.");
        }

        Map<String, Object> conversionRates = (Map<String, Object>) response.get("rates");
        Map<String, Double> processedRates = new HashMap<>();

        for (Map.Entry<String, Object> entry : conversionRates.entrySet()) {
            String currency = entry.getKey();
            Object value = entry.getValue();

    // Check if the value is an Integer and convert to Double
            if (value instanceof Integer) {
                processedRates.put(currency, ((Integer) value).doubleValue());
            } else if (value instanceof Double) {
                processedRates.put(currency, (Double) value);
            } else {
                throw new IllegalArgumentException("Unsupported value type for currency rate: " + value.getClass().getName());
            }
        }
        processedRates.forEach((currency, rate) -> {
            ExchangeRate exchangeRate = new ExchangeRate();
            exchangeRate.setCurrencyCode(currency);
            exchangeRate.setRate(rate);
            exchangeRate.setLastUpdated(LocalDateTime.now());
            //exchangeRate.setLastUpdated(conversionDate);
            // Save to the database
            exchangeRateRepository.save(exchangeRate);
        });
    }

    // Method to get the exchange rate from the database
    public double getRate(String currencyCode) {
        ExchangeRate exchangeRate = exchangeRateRepository.findByCurrencyCode(currencyCode);
        if (exchangeRate == null) {
            throw new RuntimeException("Currency not found in the database.");
        }
        return exchangeRate.getRate();
    }

    // Method to convert currency
    public Double convertCurrency(String fromCurrency, String toCurrency, Double amount) {
        Double fromRate = getRate(fromCurrency);
        Double toRate = getRate(toCurrency);
        return amount * (toRate / fromRate);
    }
}
