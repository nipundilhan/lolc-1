package com.fusionx.lending.origination.service;

import com.fusionx.lending.origination.resource.StatementTemplateAddResource;

/**
 * Statement Template Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-AUG-2021   FXL-473	      FXL-426    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
public interface StatementTemplateService {
	
	/**
	 * @author Sanatha
	 * @since 09-AUG-2021
	 * @apiNote add StatementTemplate
	 * @return ID of StatementTemplate
	 */
	Long validateAndSaveStatementTemplate(String tenantId, String createdUser, StatementTemplateAddResource statementTemplateAddResource);

}
