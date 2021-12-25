package com.fusionx.lending.transaction.enums;

public enum ExecutionStatus {

    INPROGRESS("INP", "INPROGRESS"),
    COMPLETED("COM", "COMPLETED");

    private String code;
    private String description;

    private ExecutionStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static ExecutionStatus getEnumByStatusDescription(String description) {
        ExecutionStatus obj = null;
        Object[] services = ExecutionStatus.class.getEnumConstants();

        for (int i = 0; i < services.length; i++) {
            obj = (ExecutionStatus) services[i];

            if (description.equalsIgnoreCase(obj.getDescription()))
                return obj;
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
