package com.fusionx.lending.origination.enums;

import java.util.Calendar;

public enum PeriodType {
	
	HOUR("HOUR", Calendar.HOUR),
	DATE("DATE", Calendar.DATE),
	WEEK("WEEK", Calendar.WEEK_OF_YEAR),
	MONTH("MONTH", Calendar.MONTH),
	YEAR("YEAR", Calendar.YEAR),
	MATURITY("MATURITY", 0);
	
	private String periodCode;
	private int periodUnit;
	
	private PeriodType(String periodCode, int periodUnit) {
		this.periodCode = periodCode;
		this.periodUnit = periodUnit;
	}

	public String getPeriodCode() {
		return periodCode;
	}

	public int getPeriodUnit() {
		return periodUnit;
	}

	public static PeriodType getEnumByPeriodCode(String periodCode) {
		PeriodType obj = null;
		Object[] services = PeriodType.class.getEnumConstants();
		
		for (int i = 0; i < services.length; i++) {
			obj = (PeriodType) services[i];
			
			if (periodCode.equals(obj.getPeriodCode()))
				break;
		}
		
		return obj;
	} 

}
