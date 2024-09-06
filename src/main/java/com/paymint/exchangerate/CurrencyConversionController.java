package com.paymint.exchangerate;

import com.paymint.exchangerate.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyConversionController {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @GetMapping("/convert")
    public String convertCurrency(
            @RequestParam String fromCurrency,
            @RequestParam String toCurrency,
            @RequestParam double amount) {

        double convertedAmount = exchangeRateService.convertCurrency(fromCurrency, toCurrency, amount);
        return String.format("%.2f %s is equal to %.2f %s", amount, fromCurrency, convertedAmount, toCurrency);
    }

    // Endpoint to manually trigger fetching exchange rates
    @GetMapping("/fetch-rates")
    public String fetchRates() {
        exchangeRateService.fetchAndStoreExchangeRates();
        return "Exchange rates updated successfully.";
    }
}
