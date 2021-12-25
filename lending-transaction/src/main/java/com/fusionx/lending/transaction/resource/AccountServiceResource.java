package com.fusionx.lending.transaction.resource;

/**
 * Standing Order Type service
 *
 * @author Nisalak
 * @Dat 01-11-2019
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   01-11-2019   FX-1505       FX-1125    Nisalak      Created
 * <p>
 * *******************************************************************************************************
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fusionx.lending.transaction.enums.ServiceStatus;

public class AccountServiceResource {

    private ServiceStatus serviceStatus;

    @JsonProperty(value = "AccountData")
    private AccountData accountData;

    @JsonProperty(value = "AccountValidityStatus")
    private String accountValidityStatus;

    public ServiceStatus getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(ServiceStatus serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public AccountData getAccountData() {
        return accountData;
    }

    public void setAccountData(AccountData accountData) {
        this.accountData = accountData;
    }

    public String getAccountValidityStatus() {
        return accountValidityStatus;
    }

    public void setAccountValidityStatus(String accountValidityStatus) {
        this.accountValidityStatus = accountValidityStatus;
    }

    public class AccountData {

        private String casaIdentification;

        private String schemeCode;

        private String accountName;

        private Long productId;

        private String productCode;

        private Long subProductId;

        private String subProductCode;

        private Long customerId;

        private String customerName;

        private Long accountOpenBranchOrganizationLevelId;

        private String accountOpenBranchOrganizationLevelName;

        private Long accountOpenBranchId;

        private String accountOpenBranchDescription;

        private Long accountCreateBranchOrganizationLevelId;

        private String accountCreateBranchOrganizationLevelName;

        private Long createdBranchId;

        private String createdBranchDescription;

        private Long currencyId;

        private String currencyCode;

        public String getCasaIdentification() {
            return casaIdentification;
        }

        public void setCasaIdentification(String casaIdentification) {
            this.casaIdentification = casaIdentification;
        }

        public String getSchemeCode() {
            return schemeCode;
        }

        public void setSchemeCode(String schemeCode) {
            this.schemeCode = schemeCode;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public Long getSubProductId() {
            return subProductId;
        }

        public void setSubProductId(Long subProductId) {
            this.subProductId = subProductId;
        }

        public String getSubProductCode() {
            return subProductCode;
        }

        public void setSubProductCode(String subProductCode) {
            this.subProductCode = subProductCode;
        }

        public Long getCustomerId() {
            return customerId;
        }

        public void setCustomerId(Long customerId) {
            this.customerId = customerId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public Long getAccountOpenBranchOrganizationLevelId() {
            return accountOpenBranchOrganizationLevelId;
        }

        public void setAccountOpenBranchOrganizationLevelId(Long accountOpenBranchOrganizationLevelId) {
            this.accountOpenBranchOrganizationLevelId = accountOpenBranchOrganizationLevelId;
        }

        public String getAccountOpenBranchOrganizationLevelName() {
            return accountOpenBranchOrganizationLevelName;
        }

        public void setAccountOpenBranchOrganizationLevelName(String accountOpenBranchOrganizationLevelName) {
            this.accountOpenBranchOrganizationLevelName = accountOpenBranchOrganizationLevelName;
        }

        public Long getAccountOpenBranchId() {
            return accountOpenBranchId;
        }

        public void setAccountOpenBranchId(Long accountOpenBranchId) {
            this.accountOpenBranchId = accountOpenBranchId;
        }

        public String getAccountOpenBranchDescription() {
            return accountOpenBranchDescription;
        }

        public void setAccountOpenBranchDescription(String accountOpenBranchDescription) {
            this.accountOpenBranchDescription = accountOpenBranchDescription;
        }

        public Long getAccountCreateBranchOrganizationLevelId() {
            return accountCreateBranchOrganizationLevelId;
        }

        public void setAccountCreateBranchOrganizationLevelId(Long accountCreateBranchOrganizationLevelId) {
            this.accountCreateBranchOrganizationLevelId = accountCreateBranchOrganizationLevelId;
        }

        public String getAccountCreateBranchOrganizationLevelName() {
            return accountCreateBranchOrganizationLevelName;
        }

        public void setAccountCreateBranchOrganizationLevelName(String accountCreateBranchOrganizationLevelName) {
            this.accountCreateBranchOrganizationLevelName = accountCreateBranchOrganizationLevelName;
        }

        public Long getCreatedBranchId() {
            return createdBranchId;
        }

        public void setCreatedBranchId(Long createdBranchId) {
            this.createdBranchId = createdBranchId;
        }

        public String getCreatedBranchDescription() {
            return createdBranchDescription;
        }

        public void setCreatedBranchDescription(String createdBranchDescription) {
            this.createdBranchDescription = createdBranchDescription;
        }

        public Long getCurrencyId() {
            return currencyId;
        }

        public void setCurrencyId(Long currencyId) {
            this.currencyId = currencyId;
        }

        public String getCurrencyCode() {
            return currencyCode;
        }

        public void setCurrencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
        }


    }

}
