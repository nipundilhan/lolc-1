package com.fusionx.lending.origination.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.FinancialStatement;
import com.fusionx.lending.origination.domain.FinancialStatementDetail;
import com.fusionx.lending.origination.resource.FinancialStatementDetailMainResource;
import com.fusionx.lending.origination.resource.FinancialStatementDetailNoteRequest;
import com.fusionx.lending.origination.resource.FinancialStatementDetailUpdateMainResource;

/**
 * API Service related to financial statement detail
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
 * 1        20-09-2021      -               FXL-818     Nipun Dilhan      Created
 * <p>
 *
 */

@Service
public interface FinancialStatementDetailService {
	
    /**
     * Saves the financial statement details
     *
     * @param tenantId                  the tenant id
     * @param financialStatementDetailAddResource the object to save
     * @param username                     the user
     * @param financialStatementId                     the financial statement id
     * @return the financial statement
     */	
	FinancialStatement addFinancialStatementDetail(FinancialStatementDetailMainResource financialStatementDetailAddResource,Long financialStatementId ,String user ,String tenantId );

	/**
	 * Find by financial statement id.
	 *
	 * @param finanancialStatementId - the id of the financial statement
	 * @return the list of FinancialStatementDetail
	 */
	List<FinancialStatementDetail> getDetailsByFinancialStatementId(Long finanancialStatementId);
	
    /**
     * Updates the financial statement
     *
     * @param tenantId                     the tenant id
     * @param id               the business sub center
     * @param financialStatementDetailUpdateMainResource the object to update
     * @param username                     the user
     * @return the  financial statement
     */
	FinancialStatement updateFinancialStatementDetail(Long financialStatementId,FinancialStatementDetailUpdateMainResource financialStatementDetailUpdateMainResource,String tenantId,String user);

    /**
     * Updates the financial statement detail note
     *
     * @param tenantId           the tenant id
     * @param id               the business sub center
     * @param noteList the note list to update
     * @param username                     the user
     * @return the  financial statement
     */
	FinancialStatementDetail updateNoteListStatementDetail(List<FinancialStatementDetailNoteRequest> noteList ,Long financialStatementDetailId,String user , String tenantId);

}
