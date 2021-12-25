package com.fusionx.lending.product.error.code;

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
	RESIDENCY_ELIGIBILITY_GET_ALL("/residency-eligibility/{tenantId}/all", "getAllResidencyEligibility", HttpMethod.GET, ControllerCodes.RESIDENCY_ELIGIBILITY_CONTROLLER, 1),
	RESIDENCY_ELIGIBILITY_BY_ID("/residency-eligibility/{tenantId}/{id}", "getResidencyEligibilityById", HttpMethod.GET, ControllerCodes.RESIDENCY_ELIGIBILITY_CONTROLLER, 2),
	RESIDENCY_ELIGIBILITY_DETAIL_BY_ID("/residency-eligibility/{tenantId}/detail/{residencyEligibilityId}", "getResidencyEligibilityDetailById", HttpMethod.GET, ControllerCodes.RESIDENCY_ELIGIBILITY_CONTROLLER, 3),
	RESIDENCY_ELIGIBILITY_BY_STATUS("/residency-eligibility/{tenantId}/status/{status}", "getResidencyEligibilityByStatus", HttpMethod.GET, ControllerCodes.RESIDENCY_ELIGIBILITY_CONTROLLER, 4),
	RESIDENCY_ELIGIBILITY_BY_ELIGIBILITYCODE("/residency-eligibility/{tenantId}/residency-eligibility-code/{residencyEligibilityCode}", "getResidencyEligibilityByCode", HttpMethod.GET, ControllerCodes.RESIDENCY_ELIGIBILITY_CONTROLLER, 5),
	RESIDENCY_ELIGIBILITY_ADD("/residency-eligibility/{tenantId}", "addResidencyEligibility", HttpMethod.POST, ControllerCodes.RESIDENCY_ELIGIBILITY_CONTROLLER, 6),
	RESIDENCY_ELIGIBILITY_UPDATE("/residency-eligibility/{tenantId}/{id}", "updateResidencyEligibility", HttpMethod.PUT, ControllerCodes.RESIDENCY_ELIGIBILITY_CONTROLLER, 7),
	RESIDENCY_INCLUDE_GET_ALL("/residency-include/{tenantId}/all", "getAllResidencyInclude", HttpMethod.GET, ControllerCodes.RESIDENCY_INCLUDE_CONTROLLER, 8),
	RESIDENCY_INCLUDE_GET_BY_ID("/residency-include/{tenantId}/{residencyIncludeId}", "getResidencyIncludeById", HttpMethod.GET, ControllerCodes.RESIDENCY_INCLUDE_CONTROLLER, 9),
	RESIDENCY_INCLUDE_GET_BY_RESIDENCY_ELIGIBILITY_ID("/residency-include/{tenantId}/residency-eligibility/{residencyEligibilityId}", "getResidencyIncludeByResidencyEligibilityId", HttpMethod.GET, ControllerCodes.RESIDENCY_INCLUDE_CONTROLLER, 10),
	RESIDENCY_INCLUDE_GET_BY_STATUS("/residency-include/{tenantId}/status/{status}", "getResidencyIncludeByStatus", HttpMethod.GET, ControllerCodes.RESIDENCY_INCLUDE_CONTROLLER, 11),
	RESIDENCY_INCLUDE_ADD("/residency-include/{tenantId}", "addResidencyInclude", HttpMethod.POST, ControllerCodes.RESIDENCY_INCLUDE_CONTROLLER, 12),
	RESIDENCY_INCLUDE_UPDATE("/residency-include/{tenantId}/{residencyIncludeId}", "updateResidencyInclude", HttpMethod.PUT, ControllerCodes.RESIDENCY_INCLUDE_CONTROLLER, 13),
	RESIDENCY_ELIGIBILITY_NOTES_GET_ALL("/residency-eligibility-notes/{tenantId}/all", "getResidencyEligibilityNotes", HttpMethod.GET, ControllerCodes.RESIDENCY_ELIGIBILITY_NOTES_CONTROLLER, 14),
	RESIDENCY_ELIGIBILITY_NOTES_GET_BY_ID("/residency-eligibility-notes/{tenantId}/{residencyEligibilityNotesId}", "getResidencyEligibilityNotesById", HttpMethod.GET, ControllerCodes.RESIDENCY_ELIGIBILITY_NOTES_CONTROLLER, 15),
	RESIDENCY_ELIGIBILITY_NOTES_GET_BY_RESIDENCY_ELIGIBILITY_ID("/residency-eligibility-notes/{tenantId}/residency-eligibility/{residencyEligibilityId}", "getResidencyEligibilityNotesByResidencyEligibilityId", HttpMethod.GET, ControllerCodes.RESIDENCY_ELIGIBILITY_NOTES_CONTROLLER, 16),
	RESIDENCY_ELIGIBILITY_NOTES_GET_BY_STATUS("/residency-eligibility-notes/{tenantId}/status/{status}", "getResidencyEligibilityNotesByStatus", HttpMethod.GET, ControllerCodes.RESIDENCY_ELIGIBILITY_NOTES_CONTROLLER, 17),
	RESIDENCY_ELIGIBILITY_NOTES_ADD("/residency-eligibility-notes/{tenantId}/residency-eligibility/{residencyEligibilityId}", "addResidencyEligibilityNotes", HttpMethod.POST, ControllerCodes.RESIDENCY_ELIGIBILITY_NOTES_CONTROLLER, 18),
	RESIDENCY_ELIGIBILITY_NOTES_UPDATE("/residency-eligibility-notes/{tenantId}/{residencyEligibilityNotesId}", "updateResidencyEligibilityNotes", HttpMethod.PUT, ControllerCodes.RESIDENCY_ELIGIBILITY_NOTES_CONTROLLER, 19);
	
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
