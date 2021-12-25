package com.fusionx.lending.product.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 * Message Property Base
 *
 * @author Ranjith Kodikara
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        17-07-2021      -               -           Ranjith Kodikara        Created
 * <p>
 *
 */
public abstract class MessagePropertyBase {

    @Autowired
    protected Environment environment;

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    /**
     * Common Properties
     */
    protected static final String RECORD_CREATED = "common.saved";
    protected static final String RECORD_UPDATED = "common.updated";
    protected static final String RECORD_CANCELLED = "common.cancelled";
    public static final String RECORD_NOT_FOUND = "common.record-not-found";
    public static final String TENANT_NOT_FOUND = "common.tenantNotFound";
    public static final String COMMON_INTERNAL_SERVER_ERROR = "common.internal-server-error";
    public static final String COMMON_ERROR = "common.error";
    public static final String COMMON_DUPLICATE = "common.duplicate";
    protected static final String COMMON_USER_NOT_FOUND = "common.user-not-found";
    protected static final String COMMON_NOT_NULL = "common.not-null";
    protected static final String BAD_REQUEST = "common.bad-request";
    protected static final String SERVICE_NOT_AVAILABLE = "common-service.not-available";
    protected static final String PAGEABLE_LENGTH = "common.pageable-length";
    protected static final String INVALID_VERSION = "common-invalid.version";
    protected static final String COMMON_NOT_MATCH = "common.not-match";
    protected static final String COMMON_INVALID_VALUE = "common.invalid-value";
    protected static final String ALREADY_SAVED = "already-saved";
    public static final String COMMON_CODE_UPDATE = "common.code-update";
    public static final String INVALID_STATUS = "invalid-status";
    public static final String COMMON_LIST_ITEM_NOT_FOUND = "commonListItem.not.found";
    public static final String COMMON_UPDATED = "common.updated";
    public static final String COMMON_SAVED = "common.saved";
    public static final String COMMON_APPROVED = "common.approved";
    public static final String CANT_APPROVED = "common.can-not-approved";
    public static final String COMMON_REJECTED = "common.rejected";
    public static final String CANT_REJECTED = "common.can-not-rejected";
    public static final String CANT_UPDATED = "common.can-not-update";
    public static final String INVALID_ID = "common.invalid-id";


    /**
     * JSON Properties
     */
    protected static final String VERSION = "version";
    protected static final String USER_NAME = "username";
    protected static final String TEMP_RELEASE_ID = "tempReleaseId";
    protected static final String TEMP_RELEASE_CHECK_LIST_ID = "tempReleaseCheckListId";
    /** Message Properties */
    
    protected static final String MESSAGE = "message";
    protected static final String USERNAME = "username";
    protected static final String COMMON_STATUS_PATTERN = "status.pattern";
    protected static final String COMMON_INVALID_DATE_PATTERN = "common.invalid-date-pattern";

    protected static final String INVALID_FREQ = "invalid-freq";
    protected static final String INVALID_PERIODS = "invalid-periods";
    protected static final String INVALID_PVFACTOR = "invalid-pv";
    
    public static final String COMMON_NOT_AVAILABLE = "common.not-available";

}
