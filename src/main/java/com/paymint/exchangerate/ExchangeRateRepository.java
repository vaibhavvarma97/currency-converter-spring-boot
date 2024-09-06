package com.paymint.exchangerate;

import com.paymint.exchangerate.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, String> {
    ExchangeRate findByCurrencyCode(String currencyCode);
}
