package com.fusionx.lending.origination.enums;

import org.springframework.util.AntPathMatcher;

/**
 * @author Udara Liyanage
 * @Developed with @IntelijIdea
 * @since 19/04/2021 11.13AM
 * 
 * @implNote: Specifies API Projects mapped for Fusionx Co-banking system with specific identification. 
 */

public enum ServiceCodesEnum {
	CASA_ACCOUNT("fusionx-casa", "casa-account", "FXCA-CSA", "Current Account -Saving Account Service", true),
	APPLY_SCHEDULE("fusionx-casa", "casa-apply-schedule", "FXCA-APS", "CASA Apply Schedule Service", true),
	ATM("fusionx-casa", "casa-atm", "FXCA-ATM", "CASA ATM Service", true),
	CHEQUE_BOOK_MANAGEMENT("fusionx-casa", "casa-cheque-book-management", "FXCA-CBM", "CASA Cheque Book Management Service", true),
	CASA_FUND_RESERVATION("fusionx-casa", "casa-fund-reservation", "FXCA-CFR", "CASA Fund Reservation Service", true),
	CASA_INTEREST_SCHEDULE("fusionx-casa", "casa-interest-schedule", "FXCA-CIS", "CASA Interest Schedule", true),
	CASA_MASTER_CONTROL("fusionx-casa", "casa-master-control", "FXCA-CMC", "CASA Master Control", true),
	CASA_PRODUCT_BCA("fusionx-casa", "casa-product-bca", "FXCA-CPB", "CASA Product Service", true),
	SCHEDULE("fusionx-casa", "casa-schedule", "FXCA-SCH", "CASA Schedule Service", true),
	SCHEDULED_PAYMENTS("fusionx-casa", "casa-scheduled-payments", "FXCA-CSP", "CASA Schedule Payments service", true),
	CASA_STO("fusionx-casa", "casa-sto", "FXCA-STO", "CASA Standing Order Service", true),
	CASA_TAX("fusionx-casa", "casa-tax", "FXCA-CTX", "CASA Tax Service", true),
	CASA_TRANSACTION("fusionx-casa", "casa-transaction", "FXCA-TRN", "CASA Transaction Service", true),
	
	CASA_TRANSFER_IN("fusionx-casa", "casa-transfer-in", "FXCA-CTI", "CASA Account Transafer In Service", true),
	CASA_TRANSFER_IN_OUT("fusionx-casa", "casa-transfer-in-out", "FXCA-TIO", "CASA Account Transafer In And Out Service", true),
	CASA_TRANSFER_OUT("fusionx-casa", "casa-transfer-out", "FXCA-CTO", "CASA Account Transafer Out Service", true),
	
	CENTRAL_CASH_MANAGEMENT("fusionx-cash-mng", "central-cash-management", "FXCM-CCM", "Central Cash Management Service", true),
	
	COLLATERAL("fusionx-collateral", "col-collateral", "FXCO-COL", "Collateral Service", true),
	YARD_MANAGEMENT("fusionx-collateral", "yard-management", "FXCO-YDM", "Yard Management Service", true),
	
	JASPER_REPORT_INVOKER("fusionx-common-services", "comn-jasper-report-invoke", "FXCA-JRI", "Jasper Report Invoking Service", true),
	JASPER_SERVER_PRO("fusionx-common-services", "jasperserver-pro", "FXCA-JAS", "Jasper Server", true),
	
	COMN_ALERT_MANAGEMENT("fusionx-comn", "comn-alert-management", "FXCO-AMG", "Common Alert Management Service", true),
	COMN_AUTH("fusionx-comn", "comn-auth", "FXCO-CAU", "Common Auth Service", true),
	COMN_BANK("fusionx-comn", "comn-bank", "FXCO-CBK", "Common Bank Service", true),
	COMN_BRANCH("fusionx-comn", "comn-branch", "FXCO-CBB", "Common Branch Service", true),
	FX_COMMON("fusionx-comn", "comn-common", "FXCO-CCO", "Common Common Service", true),
	COMMON_LIST("fusionx-comn", "comn-common-list", "FXCO-CCL", "Common Common Lst Service", true),
	COMN_CURRENCY_DETAIL("fusionx-comn", "comn-currency-detail", "FXCO-CUR", "Common Currency Service", true),
	COMN_CUSTOMER("fusionx-comn", "comn-customer", "FXCO-CUS", "Common Customer Service", true),
	COMN_CUSTOMER_SIGNATURE("fusionx-comn", "comn-customer-signature", "FXCO-SIG", "Customer Signature Service", true),
	COMN_DOCUMENT_UPLOAD("fusionx-comn", "comn-document-upload", "FXCO-DOC", "Document Upload Service", true),
	COMN_EMPLOYEE("fusionx-comn", "comn-employee", "FXCO-EMP", "Common Employee Service", true),
	COMN_FUSION("fusionx-comn", "comn-fusion", "FXCO-FUS", "Common Fusion Service", true), // Use this for fusion related service calls
	COMN_GEO_HIERARCHY("fusionx-comn", "comn-geo-hierarchy", "FXCO-GEO", "Common Geo Hierarchy Service", true),
	COMN_LANGUAGE("fusionx-comn", "comn-language", "FXCO-LAN", "Common Language Service", true),
	COMN_MICRO_BPR("fusionx-comn", "comn-micro-bpr", "FXCO-BPR", "Common Micro Bpr Service", true),
	COMN_ORGANIZATION("fusionx-comn", "comn-organization", "FXCO-ORG", "Common Organization Service", true),
	COMN_PERSON("fusionx-comn", "comn-person", "FXCO-PER", "Common Person Service", true),
	COMN_REPORTS("fusionx-comn", "comn-reports", "FXCO-REP", "Common Reports Service", true),
	SALES_HIERARCHY("fusionx-comn", "comn-sales-hierarchy", "FXCO-SAL", "Common Sales Hierarchy Service", true),
	COMN_SUPPLY_ENTITIES("fusionx-comn", "comn-supplies-entities", "FXCO-SUE", "Common Supply Entities Service", true),
	COMN_SYSTEM_INFO("fusionx-comn", "comn-system-info", "FXCO-SYS", "Comn System Information Service", true),
	COMN_USER("fusionx-comn", "comn-user", "FXCO-USR", "Common User Service", true),
	
	INVENTORY_MANAGEMENT("fusionx-inventory", "inv-inventory", "FXIN-INV", "Inventory Service", true),
	
	COMN_LOAN_CALCULATOR("fusionx-recovery", "comn-loan-calculator", "FXRC-CAL", "Comn Loan Calculator Service", true),
	LEGAL("fusionx-recovery", "leg-legal", "FXRC-LEG", "Legal Service", true),
	RECOVERY_MODULE("fusionx-recovery", "rec-recovery", "FXRC-REC", "Recovery Service", true),
	
	TD_ACCOUNT("fusionx-td", "td-account", "FXTD-ACC", "TD Account", true),
	TD_MODULE_FEATURE_BENEFITS("fusionx-td", "td-module-feature-benefits", "FXTD-MFB", "TD Module Feature Benefit Service", true),
	TD_PRODUCT("fusionx-td", "td-product", "FXTD-PRD", "TD Product Service", true),
	
	PAM_ENGINE("pam-engine", "pam-engine", "FXBP-PAM", "Fusionx PAM Engine Service", true),
	RULE_ENGINE("rule-engine", "rule-engine", "FXBP-RUL", "Fusionx Rule Engine Service", true),
	
	SMS_GATEWAY("fusionx-gateway", "sms-gateway", "FXGW-SMS", "Fusionx SMS Gateway Service", true),
	EMAIL_GATEWAY("fusionx-gateway", "email-gateway", "FXGW-EML", "Fusionx Email Gateway Service", true);
	
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
