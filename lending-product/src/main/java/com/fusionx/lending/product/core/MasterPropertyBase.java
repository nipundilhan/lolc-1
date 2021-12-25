package com.fusionx.lending.product.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class MasterPropertyBase {
	
	public static final String HIBERNATE_LAZY_INITIALIZER = "hibernateLazyInitializer";
	public static final String JSON_INITIALIZER_HANDLER = "handler";
	
	
	@Autowired
	protected Environment environment;
	
}
