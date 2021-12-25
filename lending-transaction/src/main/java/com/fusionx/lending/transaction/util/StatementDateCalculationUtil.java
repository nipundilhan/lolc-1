package com.fusionx.lending.transaction.util;

import com.fusionx.lending.transaction.enums.PeriodTypeEnum;
import com.fusionx.lending.transaction.resource.FrequencyResource;

import java.util.Calendar;
import java.util.Date;

public class StatementDateCalculationUtil {

    public static Date dateFrequencyCalculation(String tenantId, Date standingOrderDate, int periodUnit, FrequencyResource frequencyResource) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(standingOrderDate);

        PeriodTypeEnum periodTypeEnum = PeriodTypeEnum.getEnumByPeriodCode(frequencyResource.getType());
        if (periodTypeEnum != null) {
            calendar.add(periodTypeEnum.getPeriodUnit(), (Integer.parseInt(frequencyResource.getUnit()) * periodUnit));
        }

        return calendar.getTime();
    }

}
