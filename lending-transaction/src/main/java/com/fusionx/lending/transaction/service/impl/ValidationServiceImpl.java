package com.fusionx.lending.transaction.service.impl;

import com.fusionx.lending.transaction.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;


@Component
public class ValidationServiceImpl implements ValidationService {

    @Autowired
    private Environment environment;

    @Override
    public BigDecimal stringToBigDecimal(String value) {
        return new BigDecimal(value);
    }

    @Override
    public Timestamp getCreateOrModifyDate() {
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        return new Timestamp(now.getTime());
    }

    @Override
    public Long stringToLong(String value) {
        return Long.parseLong(value);
    }


}
