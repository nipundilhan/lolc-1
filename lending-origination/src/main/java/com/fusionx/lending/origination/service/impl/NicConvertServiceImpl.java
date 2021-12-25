package com.fusionx.lending.origination.service.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.resource.IdentificationNoConversionResource;
import com.fusionx.lending.origination.service.NicConvertService;

@Component
@Transactional(rollbackFor=Exception.class)
public class NicConvertServiceImpl implements NicConvertService{
	
	
	@Override
	public IdentificationNoConversionResource convertNic(String perIdentificationNo) {
		
		IdentificationNoConversionResource identificationConversionResource = new IdentificationNoConversionResource();
		
		int nicLength = perIdentificationNo.length();
		String conPerIdentificationNo = null;
		if (nicLength==12 || nicLength==10) {
			if (nicLength == 10) { 
				String preString=perIdentificationNo.substring(0, 5);
				System.out.println(preString);
				String postString=perIdentificationNo.substring(5,9);
				conPerIdentificationNo = "19"+preString+"0"+postString;
				System.out.println(conPerIdentificationNo);
				
				String oldNIC = perIdentificationNo.substring(0,9);
				
				identificationConversionResource.setNewNICNumber(conPerIdentificationNo);
				identificationConversionResource.setOldNICWithV(oldNIC+"V");
				identificationConversionResource.setOldNICWithX(oldNIC+"X");
				return identificationConversionResource;
			}
			else {
				String firstTwoString = perIdentificationNo.substring(0, 2);
				if (!firstTwoString.equals("20")) {
					String preString = perIdentificationNo.substring(2, 7);
					System.out.println(preString);
					String postString = perIdentificationNo.substring(8, 12);
					System.out.println(postString);
					conPerIdentificationNo = preString + postString;
					System.out.println(conPerIdentificationNo);
					
					identificationConversionResource.setNewNICNumber(perIdentificationNo);
					identificationConversionResource.setOldNICWithV(conPerIdentificationNo+"V");
					identificationConversionResource.setOldNICWithX(conPerIdentificationNo+"X");	
					return identificationConversionResource;
				}
				
			}
		}
		else {
			return identificationConversionResource;
		}
		return identificationConversionResource;
	}
}
