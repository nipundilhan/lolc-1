package com.fusionx.lending.transaction.resource;

/**
 * Standing Order Type service
 *
 * @author Nisalak
 * @Dat 05-08-2019
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   05-08-2019   FX-1505       FX-1532    Nisalak      Created
 * <p>
 * *******************************************************************************************************
 */

public class CurrencyResource {

    private String currencyCode;
    private String currencyName;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

}
