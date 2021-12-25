package com.fusionx.lending.origination.enums;

/**
 * Standing Order Type service
 * @author 	Nisalak
 * @Dat     05-08-2019
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   05-08-2019   FX-1505       FX-1532    Nisalak      Created
 *    
 ********************************************************************************************************
 */

public enum TransferType {
	INTERNAL_SAME,
	INTERNAL_THIRDPARTY,
	EXTERNAL,
	FUSION_USER_DETAILS,
	FUSION_LOD_DETAILS, 
	BUSINESS_EXPENSE_TYPE_MAPPING,
	COLLLATERAL_DOC_DETAILS,
	BANK_STATEMENT_DOC_DETAILS,
	EXTERNAL_CRIB_RELATION,
	EXTERNAL_CRIB_DET,
	CRIB_IDENTIFICATION,
	RISK_GRADING //Added by SewwandiH
}
