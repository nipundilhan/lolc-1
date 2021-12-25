package com.fusionx.lending.origination.enums;


public enum LeadStatus {

    COMPLETED(1, "Lead Completed"),
    MVCREATED(2, "MV Created"),
    BR_ASSIGNED(3, "Branch Assigned"),
    MO_ASSIGNED(4, "Marketing Officer Assigned");

    private final int id;
    private final String description;

    private LeadStatus(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public static String getDescriptionValue(LeadStatus status) {

        LeadStatus obj = null;
        Object[] services = LeadStatus.class.getEnumConstants();

        for (int i = 0; i < services.length; i++) {
            obj = (LeadStatus) services[i];

            if (status.toString().equalsIgnoreCase(obj.toString()))
                break;
        }

        return obj.getDescription();
    }

    public static LeadStatus getLeadStatus(String status) {
        LeadStatus obj = null;
        Object[] services = LeadStatus.class.getEnumConstants();

        for (int i = 0; i < services.length; i++) {
            obj = (LeadStatus) services[i];

            if (status.toString().equalsIgnoreCase(obj.toString()))
                break;

            obj = null;
        }

        return obj;
    }

    public static boolean checkValueExists(LeadStatus theStatus) {
        for (LeadStatus status : LeadStatus.values()) {
            if (theStatus.equals(status))
                return true;
        }

        return false;
    }

}
