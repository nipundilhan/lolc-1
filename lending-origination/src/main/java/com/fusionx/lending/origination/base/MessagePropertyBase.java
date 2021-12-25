package com.fusionx.lending.origination.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public abstract class MessagePropertyBase {
	
	@Autowired
	protected Environment environment;

	public Environment getEnvironment() {
		return environment;
	}
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
	
	/** Common Properties */
	protected static final String RECORD_CREATED = "common.saved";
	protected static final String RECORD_UPDATED = "common.updated";
	public static final String RECORD_NOT_FOUND = "common.record-not-found";
	public static final String TENANT_NOT_FOUND = "common.tenantNotFound";
	public static final String COMMON_INTERNAL_SERVER_ERROR = "common.internal-server-error";
	public static final String COMMON_ERROR = "common.error";
	public static final String COMMON_DUPLICATE = "common.duplicate";
	protected static final String COMMON_STATUS_PATTERN = "common-status.pattern";
	protected static final String COMMON_STATUS_NOT_NULL = "common.status-not-null";
	protected static final String COMMON_CREATEDUSER_NOT_NULL = "common.createduser-not-null";
	protected static final String COMMON_MODIFIEDUSER_NOT_NULL = "common.modifieduser-not-null";
	protected static final String COMMON_NOT_NULL = "common.not-null";
	protected static final String COMMON_NUMERIC_PATTERN = "common.numeric-pattern";
	protected static final String COMMON_INVALID_VALUE = "common.invalid-value";
	protected static final String BAD_REQUEST = "common.bad-request";
	protected static final String SERVICE_NOT_AVAILABLE = "common-service.not-available";
	protected static final String CHILD_RECORD = "common-child-record.available";
	protected static final String CHILD_RECORD_STATUS = "common-child-record-status.available";
	protected static final String PAGEABLE_LENGTH = "common.pageable-length";
	protected static final String NO_RECORD_TO_SAVED = "common.no-record";
	protected static final String INVALID_VERSION = "common-invalid.version";
	protected static final String BLANK_VERSION = "common-blank.version";
	protected static final String COMMON_NOT_MATCH = "common.not-match";
	protected static final String COMMON_UNIQUE = "common.unique";
	protected static final String COMMON_CODE_CAN_NOT_CHANGE= "common.code-cannot-change";
	protected static final String COMMON_INACTIVE= "common.status-inactive";
	
	
	protected static final String INVALID_ASSET_ENTITY_ID = "invalid.assetEntityId";
	protected static final String CUSTOMER_ID = "invalid.customerId";
	protected static final String INVALID_ASSET_DETAIL_REF = "invalid.assetEntityDetails";
	protected static final String INVALID_ASSET_ENTITY_DETAILS = "invalid.assetEntityDetails";
	protected static final String INVALID_ASSET_TYPE_ID = "invalid.asset_type_id";
	protected static final String INVALID_ASSET_SUB_TYPE_ID = "invalid.assetSubTypeId";
	protected static final String INVALID_SUB_TYPE_ID = "invalid.subTypeId";
	protected static final String ASSET_REGISTR_AUTH_ID = "invalid.regAUthorityId";
	protected static final String ASSET_SUPPLIER_ID = "invalid.assetSupplierId";
	protected static final String INVALID_PLEDGE_TYPE_ID = "invalid.assetPledgeTypeId";
	protected static final String INVALID_ASSET_DETAIL_ID = "invalid.assetDetailId";
	protected static final String INVALID_ASSET_PART_ID= "invalid.assetPartId";
	
	protected static final String COMMON_INVALID_CUSTOMER_INCOME = "common.invalid-customer-income";
	protected static final String COMMON_INVALID_GUARANTOR_INCOME = "common.invalid-guarantor-income";
	
	public static final String EMPLOYER_DUPLICATE = "employer.duplicate";
	public static final String OTHERINCOME_DUPLICATE = "otherIncome.duplicate";
	public static final String CULTIVATION_CATEGORY_DUPLICATE = "cultivationCategory.duplicate";
	public static final String BUSINESS_TYPE_DUPLICATE = "businessType.duplicate";
	
	protected static final String COMMON_INVALID_DISBURSEMENT_DETAILS = "common.invalid-disbursement-details";
	protected static final String COMMON_INVALID_DISBURSEMENT_BANK = "bank.invalid";
	protected static final String COMMON_INVALID_DISBURSEMENT_BANK_NOT_MATCH = "bank.not-match";
	protected static final String COMMON_INVALID_DISBURSEMENT_BANK_INVALID_STATUS = "bank.invalid-status";
	
	protected static final String COMMON_INVALID_DISBURSEMENT_BANK_BRANCH = "bankBranch.invalid";
	protected static final String COMMON_INVALID_DISBURSEMENT_BANK_BRANCH_NOT_MATCH = "bankBranch.not-match";
	protected static final String COMMON_INVALID_DISBURSEMENT_BANK_BRANCH_INVALID_STATUS = "bankBranch.invalid-status";
	protected static final String COMMON_INVALID_DISBURSEMENT_BANK_BRANCH_BANK_NOT_MATCH = "bankBranch-bank.not-match";
	protected static final String CUSTOMER_DUPLICATE_DISBURSEMENT_DETAILS = "customerId.duplicate-disbursement-details";
	protected static final String CUSTOMER_ID_LEAD_ID_INVALID = "customerId-leadId.invalid-disbursement-details";
	
	protected static final String COMMON_INVALID_ADDITIONAL_DOCUMENTS= "common.invalid-additional-documents";
	
	
	/** JSON Properties */
	
	protected static final String USERNAME = "username";
	protected static final String CODE = "code";
	protected static final String MESSAGE = "message";
	protected static final String VERSION = "version";
	protected static final String ID = "id";
	
	/** Message Properties */
	
	protected static final String NOT_AVAILABLE = "common.not-available";
	protected static final String NOT_FOUND = "common.record-not-found";
	protected static final String USER_NOT_FOUND = "common.user-not-found";

	protected static final String CAN_NOT_UPDATE = "exceptionType.can-not-update";
	protected static final String PENDING_NOT_FOUND = "exceptionType.record-not-found";
	
	protected static final String FUTURE_DATE_NOT_ALLOWED = "date.future";
	protected static final String PAST_DATE_NOT_ALLOWED = "date.past";
	protected static final String INVALID_INCOME_EXPENSE_TYPE_EMPTY = "invalid.income-expense-type-empty";
	protected static final String INVALID_INCOME_EXPENSE_TYPE_NOT_EMPTY= "invalid.income-expense-type-not-empty";
	protected static final String INVALID_INCOME_TYPE = "invalid.income-type";
	protected static final String INVALID_EXPENSE_TYPE= "invalid.expense-type";
	protected static final String IDENTIFICATION_LIST_NOT_NULL= "identification.not-null";
	

}
