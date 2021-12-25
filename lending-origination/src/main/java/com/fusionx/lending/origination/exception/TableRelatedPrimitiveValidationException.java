package com.fusionx.lending.origination.exception;

import com.fusionx.lending.origination.resource.TableRelatedValidationExceptionResource;

public class TableRelatedPrimitiveValidationException   extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final TableRelatedValidationExceptionResource resource;
	
	public TableRelatedPrimitiveValidationException(TableRelatedValidationExceptionResource resource) {
		//super(exception);
		this.resource = resource;
	}

	public TableRelatedValidationExceptionResource getResource() {
		return resource;
	}
	
	

}
