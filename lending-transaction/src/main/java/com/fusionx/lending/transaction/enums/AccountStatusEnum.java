package com.fusionx.lending.transaction.enums;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public enum AccountStatusEnum {
    CREATED("CR", "CREATED"),
    ACTIVE("AP", "ACTIVE"),
    REJECTED("RJ", "REJECTED"),
    DORMANT("DO", "DORMANT"),
    CLOSE_PENDING("CC", "CLOSE_PENDING"),
    CLOSE_CANCELLED("CCA", "CLOSE_CANCELLED"),
    CLOSED("CL", "CLOSED"),
    APPROVED_FINAL_WITHDRAWAL("CW", "APPROVED_FINAL_WITHDRAWAL"),
    CANCEL("CN", "CANCEL"),
    CREATE_DE_ACTIVATED("PD", "CREATE_DE_ACTIVATED"),
    DE_ACTIVATED("DA", "DEACTIVATED"),
    CREATE_RE_ACTIVATED("PR", "CREATE_RE_ACTIVATED"),
    RE_ACTIVATED("RA", "REACTIVATED"),
    CREATE_STOP_PAYMENT("CS", "CREATE_STOP_PAYMENT"),
    STOP_PAYMENT("SP", "STOP_PAYMENT"),
    CREATE_STOP_PAYMENT_REVERSAL("CSPR", "CREATE_STOP_PAYMENT_REVERSAL"),
    STOP_PAYMENT_REVERSAL("SPR", "STOP_PAYMENT_REVERSAL"),
    CREATE_BLOCK("BL", "BLOCK_PENDING"),
    BLOCK("BL", "BLOCK");

    private String statusCode;
    private String statusDescription;

    private AccountStatusEnum(String statusCode, String statusDescription) {
        this.statusCode = statusCode;
        this.statusDescription = statusDescription;
    }

    public static AccountStatusEnum getEnumByStatusCode(String serviceCode) {
        AccountStatusEnum obj = null;
        Object[] services = AccountStatusEnum.class.getEnumConstants();

        for (int i = 0; i < services.length; i++) {
            obj = (AccountStatusEnum) services[i];

            if (serviceCode.equalsIgnoreCase(obj.getStatusCode()))
                break;
            else
                obj = null;
        }

        return obj;
    }

    public static List<String> getAllAccountStatus() {
        List<String> statusList = new ArrayList<>();
        AccountStatusEnum accountStatusEnum = null;
        Object[] obj = AccountStatusEnum.class.getEnumConstants();

        for (int i = 0; i < obj.length; i++) {
            accountStatusEnum = (AccountStatusEnum) obj[i];
            statusList.add(accountStatusEnum.getStatusDescription());
        }

        return statusList;
    }

    public static AccountStatusEnum getEnumByStatusDescription(String description) {
        AccountStatusEnum obj = null;
        Object[] services = AccountStatusEnum.class.getEnumConstants();

        for (int i = 0; i < services.length; i++) {
            obj = (AccountStatusEnum) services[i];

            if (description.equalsIgnoreCase(obj.getStatusDescription()))
                return obj;
        }

        return null;
    }

    public static String getStatusByStatusCode(String serviceCode) {
        Object[] services = AccountStatusEnum.class.getEnumConstants();
        String object = "";

        for (int i = 0; i < services.length; i++) {
            AccountStatusEnum accountStatusEnum = (AccountStatusEnum) services[i];

            if (serviceCode.equalsIgnoreCase(accountStatusEnum.getStatusDescription())) {
                object = accountStatusEnum.getStatusDescription();
                return object;
            }
        }
        return null;
    }

    public static List<JSONObject> getAllAccountStatuses() throws JSONException {
        List<JSONObject> obj = new ArrayList<>();
        Object[] services = AccountStatusEnum.class.getEnumConstants();

        for (int i = 0; i < services.length; i++) {
            AccountStatusEnum accountStatusEnum = (AccountStatusEnum) services[i];

            JSONObject object = new JSONObject();
            object.put(accountStatusEnum.getStatusCode(), accountStatusEnum.getStatusDescription());
            obj.add(object);
        }

        return obj;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }
}
