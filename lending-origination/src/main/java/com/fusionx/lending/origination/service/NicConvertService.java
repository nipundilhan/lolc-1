package com.fusionx.lending.origination.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.resource.IdentificationNoConversionResource;

@Service
public interface NicConvertService {

	/**
	 * @author Venuki
	 * 
	 * Convert new NIC to old NIC
	 * */
	public IdentificationNoConversionResource convertNic(String perIdentificationNo);
}
