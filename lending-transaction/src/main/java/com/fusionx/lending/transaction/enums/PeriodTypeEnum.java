package com.fusionx.lending.transaction.enums;

import java.util.Calendar;

public enum PeriodTypeEnum {
    HOUR("HOUR", Calendar.HOUR),
    DATE("DATE", Calendar.DATE),
    WEEK("WEEK", Calendar.WEEK_OF_YEAR),
    MONTH("MONTH", Calendar.MONTH),
    YEAR("YEAR", Calendar.YEAR);

    private String periodCode;
    private int periodUnit;

    private PeriodTypeEnum(String periodCode, int periodUnit) {
        this.periodCode = periodCode;
        this.periodUnit = periodUnit;
    }

    public static PeriodTypeEnum getEnumByPeriodCode(String periodCode) {
        PeriodTypeEnum obj = null;
        Object[] services = PeriodTypeEnum.class.getEnumConstants();

        for (int i = 0; i < services.length; i++) {
            obj = (PeriodTypeEnum) services[i];

            if (periodCode.equalsIgnoreCase(obj.getPeriodCode()))
                break;
            else
                obj = null;
        }

        return obj;
    }

    public String getPeriodCode() {
        return periodCode;
    }

    public int getPeriodUnit() {
        return periodUnit;
    }
}
