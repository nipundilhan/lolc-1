package com.fusionx.lending.origination.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class MasterPropertyBase {
	
	/** Common properties **/
	protected static final boolean PROCESS_IS_APPROVAL = true;
	protected static final boolean PROCESS_IS_NOT_APPROVAL = false;
	protected static final boolean IS_HISTORY = true;
	protected static final boolean IS_NOT_HISTORY = false;
	protected static final String DEFAULT_SORT_ELEMENT = "id";

	public static final String HIBERNATE_LAZY_INITIALIZER = "hibernateLazyInitializer";
	public static final String JSON_INITIALIZER_HANDLER = "handler";
	
	/** JSON Properties **/
	protected static final String RECORD_STATUS = "status";
	protected static final String CUSTOMER_ID = "customerId";
	protected static final String ACCOUNT_NO = "accountNo";
	
	/** Message Properties **/
	protected static final String RECORD_NOT_FOUND = "common.record-not-found";
	protected static final String COMMON_INTERNAL_SERVER_ERROR = "common.internal-server-error";
	protected static final String RECORD_CREATED = "common.saved";
	protected static final String RECORD_UPDATED = "common.updated";
	protected static final String INVALID_APPROVE_METHOD = "invalid-approve.method";
	protected static final String COMMON_ERROR = "common.error";
	protected static final String TENANT_NOT_FOUND = "common.tenantNotFound";
	protected static final String STATUS_PATTERN = "common-status.pattern";
	protected static final String STATUS_NOTBLANK = "common-status.not-null";
	protected static final String CREATE_USER_NOTBLANK = "common-createuser.not-null";
	protected static final String MODIFY_USER_NOTBLANK = "common-modifyuser.not-null";
	
	@Autowired
	protected Environment environment;
	
}
