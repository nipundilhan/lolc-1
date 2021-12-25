package com.fusionx.lending.transaction.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Service
public interface ValidationService {

    public Long stringToLong(String value);

    public BigDecimal stringToBigDecimal(String value);

    public Timestamp getCreateOrModifyDate();

}
	
