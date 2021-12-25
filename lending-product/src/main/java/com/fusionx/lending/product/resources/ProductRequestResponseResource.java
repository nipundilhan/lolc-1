package com.fusionx.lending.product.resources;

public class ProductRequestResponseResource {
    private String id;
    private String version;
    private String syncTs;
    private String tenantId;
    private String code;
    private String tcsCsUrl;
    private String description;
    private String comment;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;
    private String status;
    private String earlyPaymentApplicable;
    private String overPaymentApplicable;
    private String overPayment;
    private String fullPartialRepayment;
    private String currencyId;
    private String currencyCode;
    private String currencyCodeNumeric;
    private String createdUser;
    private String createdDate;

    public ProductRequestResponseResource() {
    }

    public ProductRequestResponseResource(String id, String version, String syncTs, String tenantId, String code, String tcsCsUrl, String description, String comment, String attribute1, String attribute2, String attribute3, String attribute4, String attribute5, String status, String earlyPaymentApplicable, String overPaymentApplicable, String overPayment, String fullPartialRepayment, String currencyId, String currencyCode, String currencyCodeNumeric, String createdUser, String createdDate) {
        this.id = id;
        this.version = version;
        this.syncTs = syncTs;
        this.tenantId = tenantId;
        this.code = code;
        this.tcsCsUrl = tcsCsUrl;
        this.description = description;
        this.comment = comment;
        this.attribute1 = attribute1;
        this.attribute2 = attribute2;
        this.attribute3 = attribute3;
        this.attribute4 = attribute4;
        this.attribute5 = attribute5;
        this.status = status;
        this.earlyPaymentApplicable = earlyPaymentApplicable;
        this.overPaymentApplicable = overPaymentApplicable;
        this.overPayment = overPayment;
        this.fullPartialRepayment = fullPartialRepayment;
        this.currencyId = currencyId;
        this.currencyCode = currencyCode;
        this.currencyCodeNumeric = currencyCodeNumeric;
        this.createdUser = createdUser;
        this.createdDate = createdDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSyncTs() {
        return syncTs;
    }

    public void setSyncTs(String syncTs) {
        this.syncTs = syncTs;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTcsCsUrl() {
        return tcsCsUrl;
    }

    public void setTcsCsUrl(String tcsCsUrl) {
        this.tcsCsUrl = tcsCsUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }

    public String getAttribute3() {
        return attribute3;
    }

    public void setAttribute3(String attribute3) {
        this.attribute3 = attribute3;
    }

    public String getAttribute4() {
        return attribute4;
    }

    public void setAttribute4(String attribute4) {
        this.attribute4 = attribute4;
    }

    public String getAttribute5() {
        return attribute5;
    }

    public void setAttribute5(String attribute5) {
        this.attribute5 = attribute5;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEarlyPaymentApplicable() {
        return earlyPaymentApplicable;
    }

    public void setEarlyPaymentApplicable(String earlyPaymentApplicable) {
        this.earlyPaymentApplicable = earlyPaymentApplicable;
    }

    public String getOverPaymentApplicable() {
        return overPaymentApplicable;
    }

    public void setOverPaymentApplicable(String overPaymentApplicable) {
        this.overPaymentApplicable = overPaymentApplicable;
    }

    public String getOverPayment() {
        return overPayment;
    }

    public void setOverPayment(String overPayment) {
        this.overPayment = overPayment;
    }

    public String getFullPartialRepayment() {
        return fullPartialRepayment;
    }

    public void setFullPartialRepayment(String fullPartialRepayment) {
        this.fullPartialRepayment = fullPartialRepayment;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCodeNumeric() {
        return currencyCodeNumeric;
    }

    public void setCurrencyCodeNumeric(String currencyCodeNumeric) {
        this.currencyCodeNumeric = currencyCodeNumeric;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
