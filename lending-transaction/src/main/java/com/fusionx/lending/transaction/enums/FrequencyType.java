package com.fusionx.lending.transaction.enums;

import java.util.Calendar;

public enum FrequencyType {
    HOUR("HOUR", Calendar.HOUR),
    DATE("DATE", Calendar.DATE),
    WEEK("WEEK", Calendar.WEEK_OF_YEAR),
    MONTH("MONTH", Calendar.MONTH),
    YEAR("YEAR", Calendar.YEAR),
    MATURITY("MATURITY", 0);

    private String frequencyCode;
    private int frequncyUnit;

    private FrequencyType(String frequencyCode, int frequncyUnit) {
        this.frequencyCode = frequencyCode;
        this.frequncyUnit = frequncyUnit;
    }

    public static FrequencyType getEnumByFrequencyCode(String frequencyCode) {
        FrequencyType obj = null;
        Object[] services = FrequencyType.class.getEnumConstants();

        for (int i = 0; i < services.length; i++) {
            obj = (FrequencyType) services[i];

            if (frequencyCode.equals(obj.getFrequencyCode()))
                break;
        }

        return obj;
    }

    public String getFrequencyCode() {
        return frequencyCode;
    }

    public int getFrequncyUnit() {
        return frequncyUnit;
    }


}
