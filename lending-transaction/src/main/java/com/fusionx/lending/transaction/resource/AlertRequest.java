package com.fusionx.lending.transaction.resource;

public class AlertRequest {

    private String alertEvent;
    private String alertMask;
    private String alertType;
    private String eventCategory;
    private String module;


    public String getAlertEvent() {
        return alertEvent;
    }

    public void setAlertEvent(String alertEvent) {
        this.alertEvent = alertEvent;
    }

    public String getAlertMask() {
        return alertMask;
    }

    public void setAlertMask(String alertMask) {
        this.alertMask = alertMask;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }


}
