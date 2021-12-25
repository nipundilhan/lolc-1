package com.fusionx.lending.origination.enums;

/**
 * Common Status
 * @author Venuki
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-04-2021              Venuki      Created
 *    
 ********************************************************************************************************
 */

public enum CribReportStatus {
	
	COMPLETED("C", "COMPLETED"),
	PROGRESS("P", "PENDING"),
	NOT_APPLICABLE("NA", "NOT_APPLICABLE"),
	IN_PROGRESS("IP", "IN_PROGRESS"),
	DUPLICATE("D", "DUPLICATE"),
	SUCCESS("S", "SUCCESS"),
	ERROR("E","ERROR"),
	REQUESTED("RQT","REQUESTED");
	
	
	
	private String statusCode;
	private String statusDescription;
	
	private CribReportStatus(String statusCode, String statusDescription) {
		this.statusCode = statusCode;
		this.statusDescription = statusDescription;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public static CribReportStatus getEnumByStatusCode(String serviceCode) {
		CribReportStatus obj = null;
		Object[] objArray = CribReportStatus.class.getEnumConstants();
		
		for (int i = 0; i < objArray.length; i++) {
			obj = (CribReportStatus) objArray[i];
			
			if (serviceCode.equalsIgnoreCase(obj.getStatusCode()))
				break;
			else
				obj = null;
		}
		
		return obj;
	}
	
	public static CribReportStatus getStatusDescription(String serviceDesc) {
		CribReportStatus obj = null;
		Object[] objArray = CribReportStatus.class.getEnumConstants();
		
		for (int i = 0; i < objArray.length; i++) {
			obj = (CribReportStatus) objArray[i];
			
			if (serviceDesc.equalsIgnoreCase(obj.getStatusDescription()))
				break;
			else
				obj = null;
		}
		
		return obj;
	}
		
}
