package com.fusionx.lending.origination.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.FinancialStatement;
import com.fusionx.lending.origination.resource.DocumentRefAddResource;
import com.fusionx.lending.origination.resource.FinanceStatementAddRequest;
import com.fusionx.lending.origination.resource.FinanceStatementUpdateRequest;

/**
 * API Service related to Financial Statement.
 *
 * @author Nipun Dilhan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        17-09-2021      -               -           Nipun Dilhan      Created
 * <p>
 *
 */

@Service
public interface FinanceStatementService {
	
    /**
     * Saves the financial statement
     *
     * @param tenantId                  the tenant id
     * @param financeStatementAddRequest the object to save
     * @param username                     the user
     * @return the financial statement
     */	
	FinancialStatement addFinancialStatement(String tenantId,FinanceStatementAddRequest financeStatementAddRequest,String user);

    /**
     * Updates the financial statement
     *
     * @param tenantId                     the tenant id
     * @param id               the business sub center
     * @param financeStatementUpdateRequest the object to update
     * @param username                     the user
     * @return modified  financial statement
     */
	FinancialStatement updateFinancialStatement(Long id,String tenantId,FinanceStatementUpdateRequest financeStatementUpdateRequest,String user);

    /**
     * Updates the documents
     *
     * @param tenantId                     the tenant id
     * @param id               the business sub center
     * @param docList the object list to update
     * @param username                     the user
     * @return financial statement
     */
	FinancialStatement updateDocuments(List<DocumentRefAddResource> docList ,Long financialStatementId, String user , String tenantId);
}
