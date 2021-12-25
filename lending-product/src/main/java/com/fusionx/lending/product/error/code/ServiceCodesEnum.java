package com.fusionx.lending.product.error.code;

import org.springframework.util.AntPathMatcher;

/**
 * @author Udara Liyanage
 * @Developed with @IntelijIdea
 * @since 19/04/2021 11.13AM
 * 
 * @implNote: Specifies API Projects mapped for Fusionx Co-banking system with specific identification. 
 */

public enum ServiceCodesEnum {
	
	COLLATERAL("fusionx-collateral", "col-collateral", "FXCO-COL", "Collateral Service", true),
	COMN_ALERT_MANAGEMENT("fusionx-comn", "comn-alert-management", "FXCO-AMG", "Common Alert Management Service", true),
	FX_COMMON("fusionx-comn", "comn-common", "FXCO-CCO", "Common Common Service", true),
	COMN_CUSTOMER("fusionx-comn", "comn-customer", "FXCO-CUS", "Common Customer Service", true),
	COMN_DOCUMENT_UPLOAD("fusionx-comn", "comn-document-upload", "FXCO-DOC", "Document Upload Service", true),
	COMN_FUSION("fusionx-comn", "comn-fusion", "FXCO-FUS", "Common Fusion Service", true), // Use this for fusion related service calls
	COMN_PERSON("fusionx-comn", "comn-person", "FXCO-PER", "Common Person Service", true),
	COMN_SUPPLY_ENTITIES("fusionx-comn", "comn-supplies-entities", "FXCO-SUE", "Common Supply Entities Service", true),
	COMN_USER("fusionx-comn", "comn-user", "FXCO-USR", "Common User Service", true),
	PAM_ENGINE("pam-engine", "pam-engine", "FXBP-PAM", "Fusionx PAM Engine Service", true),
	RULE_ENGINE("rule-engine", "rule-engine", "FXBP-RUL", "Fusionx Rule Engine Service", true);

	
	private String packageName;
	private String projectName;
	private String projectCode;
	private String projectDescription;
	private boolean availability;
	
	public String getPackageName() {
		return packageName;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public String getProjectCode() {
		return projectCode;
	}
	
	public String getProjectDescription() {
		return projectDescription;
	}
	
	public boolean isAvailability() {
		return availability;
	}
	
	private ServiceCodesEnum(String packageName, String projectName, String projectCode, String projectDescription,
			boolean availability) {
		this.packageName = packageName;
		this.projectName = projectName;
		this.projectCode = projectCode;
		this.projectDescription = projectDescription;
		this.availability = availability;
	}
	
	public static ServiceCodesEnum getEnumByPattern(String requestUrl) {
		ServiceCodesEnum serviceCodesEnum = null;
		Object[] obj = ServiceCodesEnum.class.getEnumConstants();
		
		for (int i = 0; i < obj.length; i++) {
			serviceCodesEnum = (ServiceCodesEnum) obj[i];
			
			AntPathMatcher antPathMatcher = new AntPathMatcher();
			if (antPathMatcher.match("/**/"+serviceCodesEnum.getProjectName()+"/**/", requestUrl))
				break;
			else
				serviceCodesEnum = null;
		}
		
		return serviceCodesEnum;
	}
	
	public static ServiceCodesEnum getEnumByName(String name) {
		ServiceCodesEnum serviceCodesEnum = null;
		Object[] obj = ServiceCodesEnum.class.getEnumConstants();
		
		for (int i = 0; i < obj.length; i++) {
			serviceCodesEnum = (ServiceCodesEnum) obj[i];
			
			if (serviceCodesEnum.getProjectName().equalsIgnoreCase(name))
				break;
			else
				serviceCodesEnum = null;
		}
		
		return serviceCodesEnum;
	}
}
