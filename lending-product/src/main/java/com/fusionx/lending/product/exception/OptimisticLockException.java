package com.fusionx.lending.product.exception;

import javax.persistence.PersistenceException;

/**
 * Yard In Condition Service
 * @author Sanatha
 * @Date 07-OCT-2020
 *
 *********************************************************************************************************
 *  ###        Date                  Story Point         Task No              Author           Description
 *-------------------------------------------------------------------------------------------------------
 *    1        07-OCT-2020   		 FX-1429             FX-4764              Sanatha         Initial Development.
 *    
 ********************************************************************************************************
 */
public class OptimisticLockException extends PersistenceException{

	private static final long serialVersionUID = 1L;

private final String field;
	
	public OptimisticLockException (String exception,String field) {
		super(exception);
		this.field = field;
	}

	public String getField() {return this.field;}

}
