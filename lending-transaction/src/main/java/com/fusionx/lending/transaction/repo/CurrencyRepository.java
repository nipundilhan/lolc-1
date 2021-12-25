package com.fusionx.lending.transaction.repo;

import com.fusionx.lending.transaction.domain.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

}
