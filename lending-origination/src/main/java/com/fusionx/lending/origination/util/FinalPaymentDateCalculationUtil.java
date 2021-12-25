package com.fusionx.lending.origination.util;

import java.util.Calendar;
import java.util.Date;

import com.fusionx.lending.origination.enums.FrequencyType;

public class FinalPaymentDateCalculationUtil {
	
	public static Date getFinalPaymentDate(Date firstPaymentDate, String frequencyType, Long totalNoOfPayment, int noOfUnit){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(firstPaymentDate);
		FrequencyType frequencyTypeEnum = FrequencyType.getEnumByFrequencyCode(frequencyType);
		if (frequencyTypeEnum!=null) {
			calendar.add(frequencyTypeEnum.getFrequncyUnit(), (totalNoOfPayment.intValue()*noOfUnit));
		}

		return calendar.getTime();
	}
	
	// added for FX-4973
	
	public static Date getExpireDate(Date firstDate, String frequencyType, int noOfUnit){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(firstDate);
		FrequencyType frequencyTypeEnum = FrequencyType.getEnumByFrequencyCode(frequencyType);
		if (frequencyTypeEnum!=null) {
			calendar.add(frequencyTypeEnum.getFrequncyUnit(), noOfUnit);
		}

		return calendar.getTime();
	}
}
