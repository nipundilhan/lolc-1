package com.fusionx.lending.transaction.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 * Message Property Base File.
 *
 * @author ChinthakaMa
 */
public abstract class MessagePropertyBase {
	
	 protected Environment environment;

	 public Environment getEnvironment() {
	     return environment;
	 }
	 @Autowired
	 public void setEnvironment(Environment environment) {
	     this.environment = environment;
	 }

   
    /**
     * Common Properties
     */
    protected static final String COMMON_CREATED_USER_NOT_NULL = "common.createduser-not-null";
    protected static final String COMMON_MODIFIED_USER_NOT_NULL = "common.modifieduser-not-null";
    protected static final String COMMON_PROCESS_COMPLETED = "common.process-completed";
    protected  static final String INVALID_STATUS = "invalid-status";
    public static final String USER_NOT_FOUND = "common.user-not-found";
	protected static final String RECORD_CREATED = "common.saved";
	protected static final String RECORD_UPDATED = "common.updated";
	public static final String RECORD_NOT_FOUND = "common.record-not-found";
	public static final String TENANT_NOT_FOUND = "common.tenantNotFound";
	public static final String COMMON_INTERNAL_SERVER_ERROR = "common.internal-server-error";
	public static final String COMMON_ERROR = "common.error";
	public static final String COMMON_DUPLICATE = "common.duplicate";
	protected static final String COMMON_STATUS_PATTERN = "common.status-pattern";
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
	protected static final String COMMON_USER_NOT_FOUND = "common.user-not-found";
	public static final String COMMON_REJECTED = "common.rejected";
	public static final String COMMON_CODE_UPDATE = "common.code-update";
	protected static final String BANKTRANSACTIONSUBCODE_INVALID_VALUE = "banktransactionsubcode.invalid-value";
	protected static final String BANKTRANSACTIONCODE_INVALID_VALUE = "banktransactioncode.invalid-value";
	protected static final String TRANSACTIONSUBCODE_UPDATE_VALUE = "transactionsubcode.update-value";
	protected static final String WAIVE_OFF_TYPE_UPDATE_VALUE = "waiveofftype.update-value";
	protected static final String USER_UPDATE_VALUE = "user.update-value";
	protected static final String ALREADY_WAIVE_OFF_GROUP_HAS_TYPE = "waiveoffapprovalgroup.already-has-waiveofftype";
	protected static final String WAIVE_OFF_APPROVAL_GROUP_UPDATE_VALUE = "waiveoffapprovalgroup.update-value";
	protected static final String ALREADY_WAIVE_OFF_GROUP_HAS_USER = "waiveoffapprovalgroup.already-has-user";
	
	protected static final String MESSAGE = "message";

}
