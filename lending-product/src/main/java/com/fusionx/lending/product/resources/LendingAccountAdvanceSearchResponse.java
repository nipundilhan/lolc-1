package com.fusionx.lending.product.resources;
/**
 * Lending Account Detail advance search Resource
 *
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-10-2021      	-          -	     Rohan        Created
 *
 ********************************************************************************************************
 */
public class LendingAccountAdvanceSearchResponse {
    private String id;
    private String accountType;
    private String loanAppNumber;
    private String status;

    public LendingAccountAdvanceSearchResponse(String id, String accountType, String loanAppNumber, String status) {
        this.id = id;
        this.accountType = accountType;
        this.loanAppNumber = loanAppNumber;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getLoanAppNumber() {
        return loanAppNumber;
    }

    public void setLoanAppNumber(String loanAppNumber) {
        this.loanAppNumber = loanAppNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
