package com.fusionx.lending.origination.error.code;

import java.text.DecimalFormat;

import org.springframework.util.AntPathMatcher;

import io.swagger.models.HttpMethod;

/**
 * @author Rangana
 * @Developed with @IntelijIdea
 * @since 02/06/2021 04.25AM
 * 
 * @implNote: 
 * 				- Specifies methods mapped for each API request with specific identification. 
 * 				- Do not duplicate method names with same controller code.
 */

public enum RequestMapperEnum {
	APPROVAL_CATEGORY_GET_ALL("/approval-category/{tenantId}/all", "getAllApprovalCategory", HttpMethod.GET, ControllerCodes.APPROVALCATEGORYCONTROLLER, 1),
	FACILITY_FIND_BY_LEAD_ID("/facility/{tenantId}/{leadId}", "findFacilityCalculationDetailByLeadId", HttpMethod.GET, ControllerCodes.FACILITYCONTROLLER, 2),
	FACILITY_SAVE("/facility/{tenantId}/{leadId}", "saveFacility", HttpMethod.POST, ControllerCodes.FACILITYCONTROLLER, 3),
	FACILITY_UPDATE("/facility/{tenantId}/{leadId}", "updateFacility", HttpMethod.PUT, ControllerCodes.FACILITYCONTROLLER, 4);
	
	private String pathPattern;
	private String controllerName;
	private String methodName;
	private HttpMethod requestMethod;
	private String controllerCodes;
	private int methodIdentificationNo;
	
	private RequestMapperEnum(String pathPattern, String methodName, HttpMethod requestMethod,
			String controllerCodes, int methodIdentificationNo) {
		this.pathPattern = pathPattern;
		this.methodName = methodName;
		this.requestMethod = requestMethod;
		this.controllerCodes = controllerCodes;
		this.methodIdentificationNo = methodIdentificationNo;
	}

	public String getPathPattern() {
		return pathPattern;
	}

	public String getControllerName() {
		return controllerName;
	}

	public String getMethodName() {
		return methodName;
	}

	public HttpMethod getRequestMethod() {
		return requestMethod;
	}

	public String getControllerCodes() {
		return controllerCodes;
	}

	public int getMethodIdentificationNo() {
		return methodIdentificationNo;
	}
	
	public static String getErrorCode(RequestMapperEnum mapperEnum) {
		DecimalFormat format = new DecimalFormat(ControllerCodes.ERROR_CODE_FORMAT);
		return mapperEnum.getControllerCodes()+"-"+format.format(mapperEnum.methodIdentificationNo);
	}

	public static RequestMapperEnum getEnumByPattern(String requestUrl, String contextPath) {
		RequestMapperEnum requestMapperEnum = null;
		Object[] obj = RequestMapperEnum.class.getEnumConstants();
		
		for (int i = 0; i < obj.length; i++) {
			requestMapperEnum = (RequestMapperEnum) obj[i];
			
			AntPathMatcher antPathMatcher = new AntPathMatcher();
			if (antPathMatcher.match(contextPath+requestMapperEnum.getPathPattern(), requestUrl))
				break;
			else
				requestMapperEnum = null;
		}
		
		return requestMapperEnum;
	}


}
